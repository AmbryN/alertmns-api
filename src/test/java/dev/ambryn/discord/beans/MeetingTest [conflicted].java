package dev.ambryn.discord.beans;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MeetingTest {

    Meeting meeting;

    @BeforeEach
    void setup() {
        meeting = new Meeting();
    }

    @Test
    void setNameShouldTrimInput() {
        meeting.setName("   Test    ");
        assertEquals("Test", meeting.getName());
    }

    @Test
    void setNameShouldEscapeHTMLChars() {
        meeting.setName("<>&\"");
        assertEquals("&lt;&gt;&amp;&quot;", meeting.getName());
    }
}