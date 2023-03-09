package dev.ambryn.discordtest.errors.mappers;

import dev.ambryn.discordtest.enums.EError;
import dev.ambryn.discordtest.responses.ErrorResponse;
import dev.ambryn.discordtest.responses.ErrorResponseBuilder;
import jakarta.ws.rs.NotAllowedException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotAllowedWebApplicationMapper implements ExceptionMapper<NotAllowedException> {
    @Override
    public Response toResponse(NotAllowedException exception) {
        ErrorResponse error = ErrorResponseBuilder.build(EError.MethodNotAllowed, "Method not allowed", null);
        return Response
                .status(Response.Status.METHOD_NOT_ALLOWED)
                .header("Content-Type", "application/json")
                .entity(error)
                .build();
    }
}
