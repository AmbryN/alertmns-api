package dev.ambryn.discordtest.responses;

import dev.ambryn.discordtest.errors.ErrorMessage;
import jakarta.ws.rs.core.Response;

public final class ServerError {
    public static Response build(String message) {
        ErrorMessage error = new ErrorMessage(5000, message);
        return Response
                .serverError()
                .entity(error)
                .build();
    }
}
