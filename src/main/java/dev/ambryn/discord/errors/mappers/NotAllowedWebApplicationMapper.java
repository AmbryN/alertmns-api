package dev.ambryn.discord.errors.mappers;

import dev.ambryn.discord.enums.EError;
import dev.ambryn.discord.errors.Error;
import dev.ambryn.discord.responses.ErrorResponse;
import jakarta.ws.rs.NotAllowedException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotAllowedWebApplicationMapper implements ExceptionMapper<NotAllowedException> {
    @Override
    public Response toResponse(NotAllowedException exception) {
        Error.Builder builder = new Error.Builder();
        builder.setCode(EError.MethodNotAllowed);
        builder.setMessage("Method not allowed");
        Error error = builder.build();
        return Response
                .status(Response.Status.METHOD_NOT_ALLOWED)
                .header("Content-Type", "application/json")
                .entity(new ErrorResponse(error))
                .build();
    }
}
