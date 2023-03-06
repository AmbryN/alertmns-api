package dev.ambryn.discordtest.responses;

import dev.ambryn.discordtest.errors.ErrorMessage;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConflictTest {

    @Test
    void buildShouldReturnAStatus409AndErrorMessage() {
        try(Response response = Conflict.build("Test")) {
            assertEquals(409, response.getStatus());
            assertInstanceOf(ErrorMessage.class, response.getEntity());
            assertEquals("Test", ((ErrorMessage) response.getEntity()).error());
        }
    }
}