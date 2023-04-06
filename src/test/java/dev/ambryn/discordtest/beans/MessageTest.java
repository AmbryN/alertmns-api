package dev.ambryn.discordtest.beans;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {
    Message msg;

    @Test
    public void shouldEscapeHTMLChars() {
        msg = new Message();
        msg.setContent("<>&\"");
        assertEquals("&lt;&gt;&amp;&quot;", msg.getContent());
    }
}