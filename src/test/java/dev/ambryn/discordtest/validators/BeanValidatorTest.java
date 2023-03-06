package dev.ambryn.discordtest.validators;

import dev.ambryn.discordtest.dto.UserCreateDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BeanValidatorTest {
    @Test
    void validateShouldNotThrowWhenPassedAnEmptySetOfViolations() {
        Set<ConstraintViolation<Dummy>> violations = new HashSet<>();
        assertDoesNotThrow(() -> BeanValidator.validate(violations));
    }

    @Test
    void validateShouldThrowAConstraintViolationExceptionOnInvalidDTO() {
        Dummy dummyBean = new Dummy();
        Set<ConstraintViolation<Dummy>> violations = BeanValidator.computeViolations(dummyBean);
        assertThrows(ConstraintViolationException.class, () -> BeanValidator.validate(violations));
    }

    static class Dummy {
        @NotNull
        String name;
        public Dummy() {
            this.name = null;
        }
    }
}
