package com.epam.chat.datalayer.dto;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(JUnit4.class)
public class RoleTest {

    private static final String ROLE = "com.epam.chat.datalayer.dto.Role";

    @Test
    @SuppressWarnings("unchecked")
    public void verifyAllRequiredValuesPresented() throws ClassNotFoundException {
        assertTrue(Role.class.isEnum());
        try {
            Enum.valueOf((Class<? extends Enum>) Class.forName(ROLE), "ADMIN");
            Enum.valueOf((Class<? extends Enum>) Class.forName(ROLE), "USER");
        } catch (IllegalArgumentException e) {
            fail("Role enum should contains both ADMIN and USER values");
        }
    }
}
