package dev.ambryn.discord.beans;

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