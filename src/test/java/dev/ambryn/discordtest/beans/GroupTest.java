package dev.ambryn.discordtest.beans;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GroupTest {
    Group group;

    @BeforeEach
    void setup() {
        group = new Group("Groupe");
    }

    @Test
    void getNameShouldTrimInput() {
        group.setName("   Groupe&groupe_    ");
        assertEquals("Groupe&amp;groupe_", group.getName());
    }

    @Test
    void getNameShouldEscapeHTMLChars() {
        group.setName("<>&\"");
        assertEquals("&lt;&gt;&amp;&quot;", group.getName());
    }

    @Test
    void shouldAddMember() {
        User u1 = new User();
        User u2 = new User();
        group.addMember(u1);
        group.addMember(u2);
        assertEquals(u1, group.getMembers().get(0));
        assertEquals(u2, group.getMembers().get(1));
    }

    @Test
    void shouldRemoveMember() {
        User user = new User();
        group.addMember(user);
        group.removeMember(user);
        assertEquals(0, group.getMembers().size());
    }
}