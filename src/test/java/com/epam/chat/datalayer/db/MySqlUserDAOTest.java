package com.epam.chat.datalayer.db;

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
public class MySqlUserDAOTest {


    @Test
    public void packageLocation_whenExploreTheMySqlUserDAOClass_thenThePathIsCorrect() {
        assertThat(MySqlUserDAO.class.getPackage().toString(),
                is(equalTo("package com.epam.chat.datalayer.db")));
    }

    @Test
    public void interface_whenExploreTheMySqlUserDAOClass_thenMessageDAOImplemented() {
        assertTrue(UserDAO.class.isAssignableFrom(MySqlUserDAO.class));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getLast_whenExploreTheMySqlUserDAOClass_thenCurrentMethodExists() {
        Class classToExplore = MySqlMessageDAO.class;
        try {
            classToExplore.getDeclaredMethod("getLast", int.class);
        } catch (NoSuchMethodException e) {
            fail("This method should be present from the implemented class here.");
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void sendMessage_whenExploreTheMySqlUserDAOClass_thenCurrentMethodExists() {
        Class classToExplore = MySqlMessageDAO.class;
        try {
            classToExplore.getDeclaredMethod("sendMessage", Message.class);
        } catch (NoSuchMethodException e) {
            fail("This method should be present from the implemented class here.");
        }
    }


    @Test
    @SuppressWarnings("unchecked")
    public void login_whenExploreTheMySqlUserDAOClass_thenCurrentMethodExists() {
        Class classToExplore = MySqlUserDAO.class;
        try {
            classToExplore.getDeclaredMethod("login", User.class);
        } catch (NoSuchMethodException e) {
            fail("This method should be present from the implemented class here.");
        }

    }

    @Test
    @SuppressWarnings("unchecked")
    public void isLoggedIn_whenExploreTheMySqlUserDAOClass_thenCurrentMethodExists() {
        Class classToExplore = MySqlUserDAO.class;
        try {
            classToExplore.getDeclaredMethod("isLoggedIn", User.class);
        } catch (NoSuchMethodException e) {
            fail("This method should be present from the implemented class here.");
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void logout_whenExploreTheMySqlUserDAOClass_thenCurrentMethodExists() {
        Class classToExplore = MySqlUserDAO.class;
        try {
            classToExplore.getDeclaredMethod("logout", User.class);
        } catch (NoSuchMethodException e) {
            fail("This method should be present from the implemented class here.");
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void unkick_whenExploreTheMySqlUserDAOClass_thenCurrentMethodExists() {
        Class classToExplore = MySqlUserDAO.class;
        try {
            classToExplore.getDeclaredMethod("unkick", User.class);
        } catch (NoSuchMethodException e) {
            fail("This method should be present from the implemented class here.");
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void kick_whenExploreTheMySqlUserDAOClass_thenCurrentMethodExists() {
        Class classToExplore = MySqlUserDAO.class;
        try {
            classToExplore.getDeclaredMethod("kick", User.class, User.class);
        } catch (NoSuchMethodException e) {
            fail("This method should be present from the implemented class here.");
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getAllLogged_whenExploreTheMySqlUserDAOClass_thenCurrentMethodExists() {
        Class classToExplore = MySqlUserDAO.class;
        try {
            classToExplore.getDeclaredMethod("getAllLogged");
        } catch (NoSuchMethodException e) {
            fail("This method should be present from the implemented class here.");
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getAllKicked_whenExploreTheXMLUserDAOClass_thenCurrentMethodExists() {
        Class classToExplore = MySqlUserDAO.class;
        try {
            classToExplore.getDeclaredMethod("getAllKicked");
        } catch (NoSuchMethodException e) {
            fail("This method should be present from the implemented class here.");
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getRole_whenExploreTheMySqlUserDAOClass_thenCurrentMethodExists() {
        Class classToExplore = MySqlUserDAO.class;
        try {
            classToExplore.getDeclaredMethod("getRole", String.class);
        } catch (NoSuchMethodException e) {
            fail("This method should be present from the implemented class here.");
        }
    }

}
