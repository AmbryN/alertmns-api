package dev.ambryn.discordtest.responses;

import dev.ambryn.discordtest.enums.EError;
import dev.ambryn.discordtest.errors.Error;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ErrorResponseBuilderTest {

    @Test
    void shouldBuildTheAppropriateResponse() {
        List<Error> errors = List.of(new Error(EError.BadArgument, "E1"), new Error(EError.NotFound, "E2"));
        ErrorResponse response = ErrorResponseBuilder.build(EError.BadArgument, "Test", errors);
        assertEquals(EError.BadArgument, response.error().code());
        assertEquals("Test", response.error().message());
        assertArrayEquals(errors.toArray(), response.error().details().toArray());
    }

}