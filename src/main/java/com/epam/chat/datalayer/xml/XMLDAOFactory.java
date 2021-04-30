package com.epam.chat.datalayer.xml;

import com.epam.chat.datalayer.DAOFactory;
import com.epam.chat.datalayer.MessageDAO;
import com.epam.chat.datalayer.UserDAO;

public class XMLDAOFactory extends DAOFactory {

    @Override
    public MessageDAO getMessageDAO() {
        return new XMLMessageDAO(new XMLProcessor());
    }

    @Override
    public UserDAO getUserDAO() {
        return new XMLUserDAO(new XMLProcessor(), new XMLMessageDAO(new XMLProcessor()));
    }

}
