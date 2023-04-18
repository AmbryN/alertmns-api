package dev.ambryn.discordtest.responses;

import jakarta.ws.rs.core.Response;

public final class Ok {

    public static Response build() {
        return Response.ok().build();
    }

    public static Response build(Object entity) {
        return Response
                .ok()
                .header("Access-Control-Allow-Origin", "*")
                .entity(entity)
                .build();
    }
}
