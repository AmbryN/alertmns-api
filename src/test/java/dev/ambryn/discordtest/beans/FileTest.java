package dev.ambryn.discordtest.beans;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileTest {
    File file;

    @BeforeEach
    void setup() {
        file = new File("Fichier", "files/fichier.pdf");
    }
    @Test
    void getName() {
        assertEquals("Fichier", file.getName());
    }

    @Test
    void getPath() {
        assertEquals("files/fichier.pdf", file.getPath());
    }
}