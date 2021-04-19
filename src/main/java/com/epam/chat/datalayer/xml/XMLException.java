package com.epam.chat.datalayer.xml;

public class XMLException extends RuntimeException {

    public XMLException(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }
}
