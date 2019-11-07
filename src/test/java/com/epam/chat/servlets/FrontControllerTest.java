package com.epam.chat.servlets;

import com.epam.chat.commands.Command;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertNotNull;

@RunWith(JUnit4.class)
public class FrontControllerTest {

    @Ignore
    @Test
    public void defineCommandTest() {
        FrontController controller = new FrontController();
        Command command = controller.defineCommand("sendMessage");

        assertNotNull(command);
    }

}
