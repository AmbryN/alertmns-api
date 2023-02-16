package dev.ambryn.discordtest.beans;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {

    User user;

    @BeforeEach
    void setup() {
        user = new User();
    }

    @Test
    void setEmailShouldTrimAndLowerCaseInput() {
        user.setEmail("  teSt.test@test.com   ");
        assertEquals("test.test@test.com", user.getEmail());
    }

    @Test
    void setEmailShouldThrowAnExceptionIfInvalidFormat() {
        user.setEmail("ééàà@test@again");
    }

    @Test
    void setLastname() {
    }

    @Test
    void setFirstname() {
    }
}