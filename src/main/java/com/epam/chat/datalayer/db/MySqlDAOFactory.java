package com.epam.chat.datalayer.db;

import com.epam.chat.datalayer.DAOFactory;
import com.epam.chat.datalayer.MessageDAO;
import com.epam.chat.datalayer.UserDAO;
import com.epam.chat.datalayer.db.connectionpool.ConnectionPool;
import com.epam.chat.datalayer.db.connectionpool.ConnectionPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 */
public class MySqlDAOFactory extends DAOFactory {
    private static final Logger LOG = LogManager.getLogger(MySqlMessageDAO.class.getName());

    @Override
    public MessageDAO getMessageDAO() {
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            QueryManager queryManager = QueryManager.getInstance();
            return new MySqlMessageDAO(connectionPool, queryManager);
        } catch (ConnectionPoolException connectionPoolException) {
            LOG.error(connectionPoolException.getMessage(), connectionPoolException);
            throw new MySQLException(MySQLException.MYSQL_MESSAGE_DAO_ERROR, connectionPoolException);
        }
    }

    @Override
    public UserDAO getUserDAO() {
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            QueryManager queryManager = QueryManager.getInstance();
            return new MySqlUserDAO(connectionPool, queryManager);
        } catch (ConnectionPoolException connectionPoolException) {
            LOG.error(connectionPoolException.getMessage(), connectionPoolException);
            throw new MySQLException(MySQLException.MYSQL_USER_DAO_ERROR, connectionPoolException);
        }
    }


}
