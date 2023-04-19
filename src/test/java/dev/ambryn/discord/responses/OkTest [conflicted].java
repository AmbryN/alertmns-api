package dev.ambryn.discord.responses;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class OkTest {

    @Test
    void buildShouldReturnAStatus200AndTheEntity() {
        try(Response response = Ok.build("Test")) {
            assertEquals(200, response.getStatus());
            assertInstanceOf(String.class, response.getEntity());
            assertEquals("Test", response.getEntity());
        }
    }
}