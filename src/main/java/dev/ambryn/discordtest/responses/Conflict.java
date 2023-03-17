package dev.ambryn.discordtest.responses;

import dev.ambryn.discordtest.enums.EError;
import dev.ambryn.discordtest.errors.Error;
import jakarta.ws.rs.core.Response;

public final class Conflict {
    public static Response build(String message) {
        Error.Builder builder = new Error.Builder();
        builder.setCode(EError.Conflict);
        builder.setMessage(message);
        Error error = builder.build();
        return Response
                .status(Response.Status.CONFLICT)
                .entity(new ErrorResponse(error))
                .build();
    }
}
