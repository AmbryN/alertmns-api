package dev.ambryn.discordtest.responses;

import dev.ambryn.discordtest.enums.EError;
import jakarta.ws.rs.core.Response;

public class Forbidden {
    public static Response build(String message) {
        return Response
                .status(Response.Status.FORBIDDEN)
                .entity(ErrorResponseBuilder.build(EError.Forbidden, message, null))
                .build();
    }
}
