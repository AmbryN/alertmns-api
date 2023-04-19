package dev.ambryn.discord.responses;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class CreatedTest {

    @Test
    void buildShouldReturnAStatus201AndTheEntity() {
        try(Response response = Created.build("Test")) {
            assertEquals(201, response.getStatus());
            assertInstanceOf(String.class, response.getEntity());
            assertEquals("Test", response.getEntity());
        }
    }
}