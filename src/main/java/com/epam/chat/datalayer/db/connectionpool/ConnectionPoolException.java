package com.epam.chat.datalayer.db.connectionpool;

public class ConnectionPoolException extends Exception {
    public static final String SQL_INIT_ERROR =
        "SQLException in ConnectionPool initialization";
    public static final String NO_DRIVER_INIT_ERROR =
        "Can't find database driver class in ConnectionPool initialization";
    public static final String TAKE_CONNECTION_ERROR =
        "Error connecting to the data source";
    private static final long serialVersionUID = 1L;

    public ConnectionPoolException(String message, Exception e) {
        super(message, e);
    }

}
