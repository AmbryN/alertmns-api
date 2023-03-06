package dev.ambryn.discordtest.responses;

import dev.ambryn.discordtest.errors.ErrorMessage;
import jakarta.ws.rs.core.Response;

public final class Conflict {
    public static Response build(String message) {
        return Response
                .status(Response.Status.CONFLICT)
                .entity(new ErrorMessage(4009, message))
                .build();
    }
}
