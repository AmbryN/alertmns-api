package dev.ambryn.discordtest.errors.mappers;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.ArrayList;
import java.util.List;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException exception) {
        List<ConstraintViolationBean> messages = new ArrayList<>();
        for (ConstraintViolation cv : exception.getConstraintViolations()) {
            messages.add(new ConstraintViolationBean(cv));
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(messages)
                .build();
    }

    public static class ConstraintViolationBean {
        private final String property;
        private final String message;
        private final String value;

        private ConstraintViolationBean(ConstraintViolation constraintViolation) {
            String propertyPath = null;
            for (Path.Node node : constraintViolation.getPropertyPath()) {
                propertyPath = node.getName();
            }
            this.property = propertyPath;
            this.message = constraintViolation.getMessage();
            if (constraintViolation.getInvalidValue() != null) this.value = constraintViolation.getInvalidValue().toString();
            else this.value = null;
        }

        public String getProperty() {
            return property;
        }

        public String getMessage() {
            return message;
        }

        public String getValue() {
            return value;
        }
    }
}
