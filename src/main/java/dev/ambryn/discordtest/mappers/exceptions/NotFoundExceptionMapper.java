package dev.ambryn.discordtest.mappers.exceptions;

import dev.ambryn.discordtest.errors.ErrorMessage;
import jakarta.persistence.NoResultException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NoResultException> {
    @Override
    public Response toResponse(NoResultException exception) {
        ErrorMessage error = new ErrorMessage(exception.getMessage());
        return Response.status(Response.Status.NOT_FOUND)
                .entity(error)
                .build();
    }
}
