package dev.ambryn.discordtest.responses;

import dev.ambryn.discordtest.enums.EError;
import dev.ambryn.discordtest.errors.Error;
import jakarta.ws.rs.core.Response;

public final class ServerError {
    public static Response build(String message) {
        Error.Builder builder = new Error.Builder();
        builder.setCode(EError.ServerError);
        builder.setMessage(message);
        Error error = builder.build();
        return Response
                .serverError()
                .entity(new ErrorResponse(error))
                .build();
    }
}
