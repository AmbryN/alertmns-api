package dev.ambryn.discordtest.responses;

import dev.ambryn.discordtest.enums.EError;
import jakarta.ws.rs.core.*;

public final class NotFound {
    public static Response build(String message) {
        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(ErrorResponseBuilder.build(EError.NotFound, message, null))
                .build();
    }
}
