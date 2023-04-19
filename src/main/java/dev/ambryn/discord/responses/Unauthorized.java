package dev.ambryn.discord.responses;

import dev.ambryn.discord.enums.EError;
import dev.ambryn.discord.errors.Error;
import jakarta.ws.rs.core.Response;

public class Unauthorized {
    public static Response build(String message) {
        Error.Builder builder = new Error.Builder();
        builder.setCode(EError.Unauthorized);
        builder.setMessage(message);
        Error error = builder.build();
        return Response
                .status(Response.Status.UNAUTHORIZED)
                .entity(new ErrorResponse(error))
                .build();
    }
}
