package dev.ambryn.discordtest.responses;

import jakarta.ws.rs.core.Response;

public final class Created {
    public static Response build(Object dto) {
        return Response
                .status(Response.Status.CREATED)
                .entity(dto)
                .build();
    }
}
