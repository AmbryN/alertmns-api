package dev.ambryn.discordtest.validators;

import jakarta.validation.*;

import java.util.Set;

public class BeanValidator {
    public static <T> void validate(T bean) {
        try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = validatorFactory.getValidator();
            Set<ConstraintViolation<T>> violations = validator.validate(bean);
            if (violations.size() > 0) {
                throw new ConstraintViolationException(violations);
            }
        }
    }
}
