package dev.ambryn.discordtest.responses;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class NotFoundTest {

    @Test
    void buildShouldReturnAStatus404AndErrorMessage() {
        try(Response response = NotFound.build("Test")) {
            assertEquals(404, response.getStatus());
            assertInstanceOf(ErrorResponse.class, response.getEntity());
            assertEquals("Test", ((ErrorResponse) response.getEntity()).error());
        }
    }
}