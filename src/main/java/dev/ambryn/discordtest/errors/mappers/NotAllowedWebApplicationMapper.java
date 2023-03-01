package dev.ambryn.discordtest.errors.mappers;

import dev.ambryn.discordtest.errors.ErrorMessage;
import jakarta.ws.rs.NotAllowedException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotAllowedWebApplicationMapper implements ExceptionMapper<NotAllowedException> {
    @Override
    public Response toResponse(NotAllowedException exception) {
        ErrorMessage error = new ErrorMessage(4005, "Method not allowed");
        return Response
                .status(Response.Status.METHOD_NOT_ALLOWED)
                .header("Content-Type", "application/json")
                .entity(error)
                .build();
    }
}
