package dev.ambryn.discordtest.errors.mappers;

import dev.ambryn.discordtest.errors.ErrorMessage;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotFoundWebApplicationMapper implements ExceptionMapper<NotFoundException> {
    @Override
    public Response toResponse(NotFoundException exception) {
        ErrorMessage error = new ErrorMessage(4004, "Could not find ressource");
        return Response
                .status(Response.Status.NOT_FOUND)
                .header("Content-Type", "application/json")
                .entity(error)
                .build();
    }
}
