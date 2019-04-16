package com.epam.chat.datalayer.db;

import com.epam.chat.datalayer.DAOFactory;
import com.epam.chat.datalayer.MessageDAO;
import com.epam.chat.datalayer.UserDAO;

/**
 *
 */
public class OracleDAOFactory extends DAOFactory {


    @Override
    public MessageDAO getMessageDAO() {
        return new OracleMessageDAO();
    }

    @Override
    public UserDAO getUserDAO() {
        return new OracleUserDAO();
    }
}
