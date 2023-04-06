package dev.ambryn.discordtest.responses;

import dev.ambryn.discordtest.enums.EError;
import dev.ambryn.discordtest.errors.Error;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorBuilderTest {

    @Test
    void shouldBuildTheAppropriateResponse() {
        Error.Builder builder = new Error.Builder();

        Error error1 = builder.setCode(EError.BadArgument)
                .setMessage("E1")
                .build();

        Error error2 = builder.setCode(EError.NotFound)
                .setMessage("E2")
                .build();

        List<Error> errors = List.of(error1, error2);

        Error testError = builder.setCode(EError.BadArgument)
                .setMessage("Test")
                .setDetails(errors)
                .build();

        assertEquals(EError.BadArgument, testError.getCode());
        assertEquals("Test", testError.getMessage());
        assertArrayEquals(errors.toArray(), testError.getDetails().toArray());
    }

}