package dev.ambryn.discordtest.responses;

import dev.ambryn.discordtest.enums.EError;
import dev.ambryn.discordtest.errors.Error;
import jakarta.ws.rs.core.Response;

public class Forbidden {
    public static Response build(String message) {
        Error.Builder builder = new Error.Builder();
        builder.setCode(EError.Forbidden);
        builder.setMessage(message);
        Error error = builder.build();
        return Response
                .status(Response.Status.FORBIDDEN)
                .entity(new ErrorResponse(error))
                .build();
    }
}
