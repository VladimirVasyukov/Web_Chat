package com.epam.chat.datalayer.xml;

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
public class XMLMessageDAOTest {

    @Test
    public void packageLocation_whenExploreTheXMLMessageDAOClass_thenThePathIsCorrect() {
        assertThat(XMLMessageDAO.class.getPackage().toString(),
                is(equalTo("package com.epam.chat.datalayer.xml")));
    }

    @Test
    public void interface_whenExploreTheXMLMessageDAOClass_thenMessageDAOImplemented() {
        assertTrue(MessageDAO.class.isAssignableFrom(XMLMessageDAO.class));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getLast_whenExploreTheXMLMessageDAOClass_thenCurrentMethodExists() {
        Class classToExplore = XMLMessageDAO.class;
        try {
            classToExplore.getDeclaredMethod("getLast", int.class);
        } catch (NoSuchMethodException e) {
            fail("This method should be present from the implemented class here.");
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void sendMessage_whenExploreTheClass_thenCurrentMethodExists() {
        Class classToExplore = XMLMessageDAO.class;
        try {
            classToExplore.getDeclaredMethod("sendMessage", Message.class);
        } catch (NoSuchMethodException e) {
            fail("This method should be present from the implemented class here.");
        }
    }

}
