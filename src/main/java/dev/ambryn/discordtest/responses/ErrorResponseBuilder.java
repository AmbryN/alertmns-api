package dev.ambryn.discordtest.responses;

import dev.ambryn.discordtest.enums.EError;
import dev.ambryn.discordtest.errors.Error;

import java.util.List;

public class ErrorResponseBuilder {
    public static ErrorResponse build(EError code, String message, List<Error> subErrors) {
        Error error = new Error(code, null, message, subErrors);
        return new ErrorResponse(error);
    }
}
