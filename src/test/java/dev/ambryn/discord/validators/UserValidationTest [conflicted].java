package dev.ambryn.discord.validators;

import dev.ambryn.discord.dto.UserCreateDTO;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserValidationTest {

    @Test
    void computeViolationsShouldContainNoneIfPassedAValidDTO() {
        UserCreateDTO dto = new UserCreateDTO("email@email.com", "password", "test", "test");
        Set<ConstraintViolation<UserCreateDTO>> violations = BeanValidator.computeViolations(dto);
        assertEquals(0, violations.size());
    }

    @Test
    void computeViolationsShouldContainViolations() {
        UserCreateDTO dto = new UserCreateDTO("email.com", "pass", "@", "");
        Set<ConstraintViolation<UserCreateDTO>> violations = BeanValidator.computeViolations(dto);
        // Should contain
        // Email : Pattern violation (not an email)
        // Password : Length violation (less than 8 characters)
        // Lastname : Length violation (less than 2 characters), Pattern violation (special character)
        // Firstname : Should not be empty, Length violation (empty), Pattern violation (empty)
        assertEquals(7, violations.size());
    }
}