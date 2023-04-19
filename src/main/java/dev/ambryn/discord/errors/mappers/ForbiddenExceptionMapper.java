package dev.ambryn.discord.errors.mappers;

import dev.ambryn.discord.enums.EError;
import dev.ambryn.discord.errors.Error;
import dev.ambryn.discord.responses.ErrorResponse;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ForbiddenExceptionMapper implements ExceptionMapper<ForbiddenException> {
    @Override
    public Response toResponse(ForbiddenException exception) {
        Error.Builder builder = new Error.Builder();
        builder.setCode(EError.Forbidden);
        builder.setMessage(exception.getMessage());
        Error error = builder.build();
        return Response
                .status(Response.Status.FORBIDDEN)
                .header("Content-Type", "application/json")
                .entity(new ErrorResponse(error))
                .build();
    }
}
