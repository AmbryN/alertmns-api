package dev.ambryn.discordtest.responses;

import dev.ambryn.discordtest.enums.EError;
import dev.ambryn.discordtest.errors.Error;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ErrorBuilderTest {

    @Test
    void shouldBuildTheAppropriateResponse() {
        Error.Builder builder = new Error.Builder();

        builder.setCode(EError.BadArgument);
        builder.setMessage("E1");
        Error error1 = builder.build();

        builder.setCode(EError.NotFound);
        builder.setMessage("E2");
        Error error2 = builder.build();

        List<Error> errors = List.of(error1, error2);

        builder.setCode(EError.BadArgument);
        builder.setMessage("Test");
        builder.setDetails(errors);
        Error testError = builder.build();

        assertEquals(EError.BadArgument, testError.getCode());
        assertEquals("Test", testError.getMessage());
        assertArrayEquals(errors.toArray(), testError.getDetails().toArray());
    }

}