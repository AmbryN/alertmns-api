package dev.ambryn.discordtest.responses;

import dev.ambryn.discordtest.enums.EError;
import dev.ambryn.discordtest.errors.Error;
import jakarta.ws.rs.core.*;

public final class NotFound {
    public static Response build(String message) {
        Error.Builder builder = new Error.Builder();
        builder.setCode(EError.NotFound);
        builder.setMessage(message);
        Error error = builder.build();
        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(new ErrorResponse(error))
                .build();
    }
}
