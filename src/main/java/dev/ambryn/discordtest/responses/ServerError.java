package dev.ambryn.discordtest.responses;

import dev.ambryn.discordtest.enums.EError;
import jakarta.ws.rs.core.Response;

public final class ServerError {
    public static Response build(String message) {
        ErrorResponse error = ErrorResponseBuilder.build(EError.ServerError, message, null);
        return Response
                .serverError()
                .entity(error)
                .build();
    }
}
