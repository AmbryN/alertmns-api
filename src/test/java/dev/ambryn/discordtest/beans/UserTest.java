package dev.ambryn.discordtest.beans;

import dev.ambryn.discordtest.errors.mappers.ValidationExceptionMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTest {

    ValidationExceptionMapper em;

    User user;

    @BeforeEach
    void setup() {
        user = new User();
    }

    @Test
    void setEmailShouldTrimAndLowercaseInput() {
        user.setEmail("  TEST.test@test.com   ");
        assertEquals("test.test@test.com", user.getEmail());
    }

    @Test
    void setLastnameShouldTrimAndUppercaseInput() {
        user.setLastname("    de la fontaine ");
        assertEquals("DE LA FONTAINE", user.getLastname());
    }

    @Test
    void setLastnameShouldEscapeHTMLChars() {
        user.setLastname("<>&\"");
        assertEquals("&lt;&gt;&amp;&quot;", user.getLastname());
    }

    @Test
    void setFirstnameShouldTrimInput() {
        user.setFirstname("    Jean-Christophe   ");
        assertEquals("Jean-Christophe", user.getFirstname());
    }

    @Test
    void setFirstnameShouldEscapeHTMLChars() {
        user.setFirstname("<>&\"");
        assertEquals("&lt;&gt;&amp;&quot;", user.getFirstname());
    }
}