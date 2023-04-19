package dev.ambryn.discord.responses;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class ForbiddenTest {

    @Test
    void buildShouldReturnAStatus403AndErrorMessage() {
        try(Response response = Forbidden.build("Test")) {
            assertEquals(403, response.getStatus());
            assertInstanceOf(ErrorResponse.class, response.getEntity());
            assertEquals("Test", ((ErrorResponse) response.getEntity()).error().getMessage());
        }
    }
}