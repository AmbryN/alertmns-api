package dev.ambryn.discordtest.errors.mappers;

import dev.ambryn.discordtest.enums.EError;
import dev.ambryn.discordtest.errors.Error;
import dev.ambryn.discordtest.responses.ErrorResponse;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotFoundWebApplicationMapper implements ExceptionMapper<NotFoundException> {
    @Override
    public Response toResponse(NotFoundException exception) {
        Error.Builder builder = new Error.Builder();
        builder.setCode(EError.NotFound);
        builder.setMessage(exception.getMessage());
        Error error = builder.build();
        return Response
                .status(Response.Status.NOT_FOUND)
                .header("Content-Type", "application/json")
                .entity(new ErrorResponse(error))
                .build();
    }
}
