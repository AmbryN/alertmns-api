package dev.ambryn.discordtest.errors.mappers;

import dev.ambryn.discordtest.enums.EError;
import dev.ambryn.discordtest.errors.Error;
import dev.ambryn.discordtest.responses.ErrorResponse;
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
        List<Error> errors = new ArrayList<>();
        for (ConstraintViolation cv : exception.getConstraintViolations()) {
            ConstraintViolationBean cvb = new ConstraintViolationBean(cv);
            Error.Builder builder = new Error.Builder();
            builder.setCode(EError.BadArgument);
            builder.setTarget(cvb.getTarget());
            builder.setValue(cvb.getValue());
            builder.setMessage(cvb.getMessage());
            Error error = builder.build();
            messages.add(new ConstraintViolationBean(cv));
            errors.add(error);
        }

        Error.Builder builder = new Error.Builder();
        builder.setCode(EError.BadArgument);
        builder.setDetails(errors);
        Error error = builder.build();

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse(error))
                .build();
    }

    public static class ConstraintViolationBean {
        private final String target;
        private final String message;
        private final String value;

        private ConstraintViolationBean(ConstraintViolation constraintViolation) {
            String propertyPath = null;
            for (Path.Node node : constraintViolation.getPropertyPath()) {
                propertyPath = node.getName();
            }
            this.target = propertyPath;
            this.message = constraintViolation.getMessage();
            if (constraintViolation.getInvalidValue() != null) this.value = constraintViolation.getInvalidValue().toString();
            else this.value = null;
        }

        public String getTarget() {
            return target;
        }

        public String getMessage() {
            return message;
        }

        public String getValue() {
            return value;
        }
    }
}
