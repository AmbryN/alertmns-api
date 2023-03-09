package dev.ambryn.discordtest.responses;

import dev.ambryn.discordtest.enums.EError;
import jakarta.ws.rs.core.Response;

public final class Conflict {
    public static Response build(String message) {
        return Response
                .status(Response.Status.CONFLICT)
                .entity(ErrorResponseBuilder.build(EError.Conflict, message, null))
                .build();
    }
}
