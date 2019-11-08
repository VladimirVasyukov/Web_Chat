package com.epam.chat.datalayer.db;

import com.epam.chat.datalayer.DAOFactory;
import com.epam.chat.datalayer.MessageDAO;
import com.epam.chat.datalayer.UserDAO;

/**
 *
 */
public class MySqlDAOFactory extends DAOFactory {


    @Override
    public MessageDAO getMessageDAO() {
        return new MySqlMessageDAO();
    }

    @Override
    public UserDAO getUserDAO() {
        return new MySqlUserDAO();
    }
}
