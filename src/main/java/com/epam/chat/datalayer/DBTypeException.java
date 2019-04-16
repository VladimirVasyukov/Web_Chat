package com.epam.chat.datalayer;

/**
 * Exception when db type not found
 */
public class DBTypeException extends RuntimeException {

    public DBTypeException() {
        super();
    }

    public DBTypeException(String message) {
        super(message);
    }
}
