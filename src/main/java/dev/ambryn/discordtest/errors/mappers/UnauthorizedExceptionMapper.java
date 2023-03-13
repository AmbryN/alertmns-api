package dev.ambryn.discordtest.errors.mappers;

import dev.ambryn.discordtest.enums.EError;
import dev.ambryn.discordtest.errors.UnauthorizedException;
import dev.ambryn.discordtest.responses.ErrorResponse;
import dev.ambryn.discordtest.responses.ErrorResponseBuilder;
import jakarta.ws.rs.NotAllowedException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class UnauthorizedExceptionMapper implements ExceptionMapper<UnauthorizedException> {
    @Override
    public Response toResponse(UnauthorizedException exception) {
        ErrorResponse error = ErrorResponseBuilder.build(EError.Unauthorized, exception.getMessage(), null);
        return Response
                .status(Response.Status.UNAUTHORIZED)
                .header("Content-Type", "application/json")
                .entity(error)
                .build();
    }
}
