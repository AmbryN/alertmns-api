package dev.ambryn.discord.errors.mappers;

import dev.ambryn.discord.enums.EError;
import dev.ambryn.discord.errors.Error;
import dev.ambryn.discord.errors.UnauthorizedException;
import dev.ambryn.discord.responses.ErrorResponse;
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
