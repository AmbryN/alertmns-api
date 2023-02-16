package dev.ambryn.discordtest.beans;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {
    Message msg;

    @BeforeEach
    void setup() {
        msg = new Message(new User(), "Content", null);
    }

    @Test
    @Disabled
    void getSender() {
    }

    @Test
    void getContent() {
        assertEquals("Content", msg.getContent());
    }

    @Test
    void getFile() {
        assertNull(msg.getFile());
    }
}