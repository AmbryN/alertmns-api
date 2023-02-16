package dev.ambryn.discordtest.beans;

import dev.ambryn.discordtest.enums.EVisibility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VisibilityTest {

    Visibility visibility;
    @BeforeEach
    void setup() {
        visibility = new Visibility(EVisibility.PUBLIC);
    }


    @Test
    void getName() {
        assertEquals(EVisibility.PUBLIC, visibility.getName());
    }
}