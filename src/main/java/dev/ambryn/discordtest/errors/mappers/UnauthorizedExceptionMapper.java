package dev.ambryn.discordtest.errors.mappers;

import dev.ambryn.discordtest.enums.EError;
import dev.ambryn.discordtest.errors.Error;
import dev.ambryn.discordtest.errors.UnauthorizedException;
import dev.ambryn.discordtest.responses.ErrorResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class UnauthorizedExceptionMapper implements ExceptionMapper<UnauthorizedException> {
    @Override
    public Response toResponse(UnauthorizedException exception) {
        Error.Builder builder = new Error.Builder();
        builder.setCode(EError.Unauthorized);
        builder.setMessage(exception.getMessage());
        Error error = builder.build();
        return Response
                .status(Response.Status.UNAUTHORIZED)
                .header("Content-Type", "application/json")
                .entity(new ErrorResponse(error))
                .build();
    }
}
