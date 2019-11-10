package com.epam.chat.datalayer.xml;

import com.epam.chat.datalayer.DAOFactory;
import com.epam.chat.datalayer.DBType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.TestCase.assertTrue;

@RunWith(JUnit4.class)
public class XMLDAOFactoryTest {

    private DAOFactory daoFactory = DAOFactory.getInstance(DBType.XML);

    @Test
    public void getMessageDAO_whenMethodCalled_thenReturnXMLImplementation() {
        assertTrue(daoFactory.getMessageDAO() instanceof XMLMessageDAO);
    }

    @Test
    public void getUserDAO_whenMethodCalled_thenReturnXMLImplementation() {
        assertTrue(daoFactory.getUserDAO() instanceof XMLUserDAO);
    }
}
