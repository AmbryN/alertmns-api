package dev.ambryn.discordtest.responses;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class UnauthorizedTest {

    @Test
    void buildShouldReturnAStatus403AndErrorMessage() {
        try(Response response = Unauthorized.build("Test")) {
            assertEquals(401, response.getStatus());
            assertInstanceOf(ErrorResponse.class, response.getEntity());
            assertEquals("Test", ((ErrorResponse) response.getEntity()).error().getMessage());
        }
    }
}