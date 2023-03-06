package dev.ambryn.discordtest.responses;

import dev.ambryn.discordtest.errors.ErrorMessage;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class ServerErrorTest {

    @Test
    void buildShouldReturnAStatus500AndErrorMessage() {
        try(Response response = ServerError.build("Test")) {
            assertEquals(500, response.getStatus());
            assertInstanceOf(ErrorMessage.class, response.getEntity());
            assertEquals("Test", ((ErrorMessage) response.getEntity()).error());
        }
    }
}