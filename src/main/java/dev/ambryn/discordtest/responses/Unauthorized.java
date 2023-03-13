package dev.ambryn.discordtest.responses;

import dev.ambryn.discordtest.enums.EError;
import jakarta.ws.rs.core.Response;

public class Unauthorized {
    public static Response build(String message) {
        return Response
                .status(Response.Status.UNAUTHORIZED)
                .entity(ErrorResponseBuilder.build(EError.Unauthorized, message, null))
                .build();
    }
}
