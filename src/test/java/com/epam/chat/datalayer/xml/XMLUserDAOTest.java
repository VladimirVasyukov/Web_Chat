package com.epam.chat.datalayer.xml;

import com.epam.chat.datalayer.UserDAO;
import com.epam.chat.datalayer.dto.Message;
import com.epam.chat.datalayer.dto.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

@RunWith(JUnit4.class)
public class XMLUserDAOTest {


    @Test
    public void packageLocation_whenExploreTheXMLUserDAOClass_thenThePathIsCorrect() {
        assertThat(XMLUserDAO.class.getPackage().toString(),
                is(equalTo("package com.epam.chat.datalayer.xml")));
    }

    @Test
    public void interface_whenExploreTheXMLUserDAOClass_thenMessageDAOImplemented() {
        assertTrue(UserDAO.class.isAssignableFrom(XMLUserDAO.class));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getLast_whenExploreTheXMLUserDAOClass_thenCurrentMethodExists() {
        Class classToExplore = XMLMessageDAO.class;
        try {
            classToExplore.getDeclaredMethod("getLast", int.class);
        } catch (NoSuchMethodException e) {
            fail("This method should be present from the implemented class here.");
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void sendMessage_whenExploreTheXMLUserDAOClass_thenCurrentMethodExists() {
        Class classToExplore = XMLMessageDAO.class;
        try {
            classToExplore.getDeclaredMethod("sendMessage", Message.class);
        } catch (NoSuchMethodException e) {
            fail("This method should be present from the implemented class here.");
        }
    }


    @Test
    @SuppressWarnings("unchecked")
    public void login_whenExploreTheXMLUserDAOClass_thenCurrentMethodExists() {
        Class classToExplore = XMLUserDAO.class;
        try {
            classToExplore.getDeclaredMethod("login", User.class);
        } catch (NoSuchMethodException e) {
            fail("This method should be present from the implemented class here.");
        }

    }

    @Test
    @SuppressWarnings("unchecked")
    public void isLoggedIn_whenExploreTheXMLUserDAOClass_thenCurrentMethodExists() {
        Class classToExplore = XMLUserDAO.class;
        try {
            classToExplore.getDeclaredMethod("isLoggedIn", User.class);
        } catch (NoSuchMethodException e) {
            fail("This method should be present from the implemented class here.");
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void logout_whenExploreTheXMLUserDAOClass_thenCurrentMethodExists() {
        Class classToExplore = XMLUserDAO.class;
        try {
            classToExplore.getDeclaredMethod("logout", User.class);
        } catch (NoSuchMethodException e) {
            fail("This method should be present from the implemented class here.");
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void unkick_whenExploreTheXMLUserDAOClass_thenCurrentMethodExists() {
        Class classToExplore = XMLUserDAO.class;
        try {
            classToExplore.getDeclaredMethod("unkick", User.class);
        } catch (NoSuchMethodException e) {
            fail("This method should be present from the implemented class here.");
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void kick_whenExploreTheXMLUserDAOClass_thenCurrentMethodExists() {
        Class classToExplore = XMLUserDAO.class;
        try {
            classToExplore.getDeclaredMethod("kick", User.class, User.class);
        } catch (NoSuchMethodException e) {
            fail("This method should be present from the implemented class here.");
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getAllLogged_whenExploreTheXMLUserDAOClass_thenCurrentMethodExists() {
        Class classToExplore = XMLUserDAO.class;
        try {
            classToExplore.getDeclaredMethod("getAllLogged");
        } catch (NoSuchMethodException e) {
            fail("This method should be present from the implemented class here.");
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getAllKicked_whenExploreTheXMLUserDAOClass_thenCurrentMethodExists() {
        Class classToExplore = XMLUserDAO.class;
        try {
            classToExplore.getDeclaredMethod("getAllKicked");
        } catch (NoSuchMethodException e) {
            fail("This method should be present from the implemented class here.");
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getRole_whenExploreTheXMLUserDAOClass_thenCurrentMethodExists() {
        Class classToExplore = XMLUserDAO.class;
        try {
            classToExplore.getDeclaredMethod("getRole", String.class);
        } catch (NoSuchMethodException e) {
            fail("This method should be present from the implemented class here.");
        }
    }

}
