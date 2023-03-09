package dev.ambryn.discordtest.errors.mappers;

import dev.ambryn.discordtest.enums.EError;
import dev.ambryn.discordtest.responses.ErrorResponse;
import dev.ambryn.discordtest.responses.ErrorResponseBuilder;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class JsonParseExceptionMapper implements ExceptionMapper<ProcessingException> {
    @Override
    public Response toResponse(ProcessingException exception) {
        String message = exception.getCause().getMessage();
        ErrorResponse error = ErrorResponseBuilder.build(EError.BadArgument, message, null);
        return Response
                .status(Response.Status.BAD_REQUEST)
                .header("Content-Type", "application/json")
                .entity(error)
                .build();
    }
}
