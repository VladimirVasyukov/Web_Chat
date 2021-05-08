package com.epam.chat.datalayer.db.connectionpool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public final class ConnectionPool {
    private static final int DEFAULT_POOL_SIZE = 10;
    private static final Logger LOG = LogManager.getLogger(ConnectionPool.class.getName());
    private static final String CLOSING_CONNECTION_ERROR = "Error closing the connection";
    private static ConnectionPool connectionPool;
    private BlockingQueue<Connection> connectionQueue;
    private BlockingQueue<Connection> givenAwayConQueue;
    private final String driverName;
    private final String url;
    private final String user;
    private final String password;
    private int poolSize;

    private ConnectionPool() {
        DBResourceManager dbResourceManager = DBResourceManager.getInstance();
        this.driverName = dbResourceManager.getValue(DBParameter.DB_DRIVER);
        this.url = dbResourceManager.getValue(DBParameter.DB_URL);
        this.user = dbResourceManager.getValue(DBParameter.DB_USER);
        this.password = dbResourceManager.getValue(DBParameter.DB_PASSWORD);
        try {
            this.poolSize = Integer.parseInt(dbResourceManager.getValue(DBParameter.DB_POLL_SIZE));
        } catch (NumberFormatException e) {
            poolSize = DEFAULT_POOL_SIZE;
        }
    }

    public static ConnectionPool getInstance() throws ConnectionPoolException {
        if (connectionPool == null) {
            connectionPool = new ConnectionPool();
            connectionPool.initPoolData();
        }
        return connectionPool;
    }

    public void initPoolData() throws ConnectionPoolException {
        Locale.setDefault(Locale.ENGLISH);
        try {
            Class.forName(driverName);
            givenAwayConQueue = new ArrayBlockingQueue<>(poolSize);
            connectionQueue = new ArrayBlockingQueue<>(poolSize);
            for (int i = 0; i < poolSize; i++) {
                Connection connection = DriverManager.getConnection(url, user, password);
                PooledConnection pooledConnection = new PooledConnection(connection, this);
                connectionQueue.add(pooledConnection);
            }
        } catch (SQLException e) {
            throw new ConnectionPoolException(ConnectionPoolException.SQL_INIT_ERROR, e);
        } catch (ClassNotFoundException e) {
            throw new ConnectionPoolException(ConnectionPoolException.NO_DRIVER_INIT_ERROR, e);
        }
    }

    public Connection takeConnection() throws ConnectionPoolException {
        Connection connection;
        try {
            connection = connectionQueue.take();
            givenAwayConQueue.add(connection);
        } catch (InterruptedException e) {
            throw new ConnectionPoolException(ConnectionPoolException.TAKE_CONNECTION_ERROR, e);
        }
        return connection;
    }

    public boolean removeFromGivenAwayConQueue(Connection con) {
        return givenAwayConQueue.remove(con);
    }

    public boolean offerToConnectionQueue(Connection con) {
        return connectionQueue.offer(con);
    }

    private void clearConnectionQueue() {
        try {
            closeConnectionsQueue(givenAwayConQueue);
            closeConnectionsQueue(connectionQueue);
        } catch (SQLException e) {
            LOG.error(CLOSING_CONNECTION_ERROR, e);
        }
    }

    private void closeConnectionsQueue(BlockingQueue<Connection> queue)
        throws SQLException {
        Connection connection;
        while ((connection = queue.poll()) != null) {
            if (!connection.getAutoCommit()) {
                connection.commit();
            }
            ((PooledConnection) connection).reallyClose();
        }
    }

}
