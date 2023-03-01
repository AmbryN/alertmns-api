package dev.ambryn.discordtest.responses;

import dev.ambryn.discordtest.errors.ErrorMessage;
import jakarta.ws.rs.core.*;

public final class NotFound {
    public static Response build(String message) {
        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(new ErrorMessage(4004, message))
                .build();
    }
}
