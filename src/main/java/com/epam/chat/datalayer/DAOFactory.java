package com.epam.chat.datalayer;

public abstract class DAOFactory {

    public static DAOFactory getInstance(DBType dbType) {
        return dbType.getDAOFactory();
    }

    public abstract MessageDAO getMessageDAO();

    public abstract UserDAO getUserDAO();
}
