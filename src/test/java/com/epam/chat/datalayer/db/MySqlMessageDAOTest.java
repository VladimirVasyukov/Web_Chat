package com.epam.chat.datalayer.db;

import com.epam.chat.datalayer.MessageDAO;
import com.epam.chat.datalayer.dto.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

@RunWith(JUnit4.class)
public class MySqlMessageDAOTest {

    @Test
    public void packageLocation_whenExploreTheMySqlMessageDAOClass_thenThePathIsCorrect() {
        assertThat(MySqlMessageDAO.class.getPackage().toString(),
                is(equalTo("package com.epam.chat.datalayer.db")));
    }

    @Test
    public void interface_whenExploreTheMySqlMessageDAOClass_thenMessageDAOImplemented() {
        assertTrue(MessageDAO.class.isAssignableFrom(MySqlMessageDAO.class));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getLast_whenExploreTheMySqlMessageDAOClass_thenCurrentMethodExists() {
        Class classToExplore = MySqlMessageDAO.class;
        try {
            classToExplore.getDeclaredMethod("getLast", int.class);
        } catch (NoSuchMethodException e) {
            fail("This method should be present from the implemented class here.");
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void sendMessage_whenExploreTheMySqlMessageDAOClass_thenCurrentMethodExists() {
        Class classToExplore = MySqlMessageDAO.class;
        try {
            classToExplore.getDeclaredMethod("sendMessage", Message.class);
        } catch (NoSuchMethodException e) {
            fail("This method should be present from the implemented class here.");
        }
    }

}
