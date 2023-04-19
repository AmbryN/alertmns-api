package dev.ambryn.discord.validators;

import dev.ambryn.discord.dto.ChannelCreateDTO;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChannelValidationTest {

    @Test
    void computeViolationsShouldContainNoneIfPassedAValidDTO() {
        ChannelCreateDTO dto = new ChannelCreateDTO("My Channel", "PUBLIC");
        Set<ConstraintViolation<ChannelCreateDTO>> violations = BeanValidator.computeViolations(dto);
        assertEquals(0, violations.size());
    }

    @Test
    void computeViolationsShouldContainViolations() {
        ChannelCreateDTO dto = new ChannelCreateDTO("", "INVALID VISIBILITY");
        Set<ConstraintViolation<ChannelCreateDTO>> violations = BeanValidator.computeViolations(dto);
        // Should contain
        // Name : Should not be empty, Size violation (less than 2 characters)
        // Visibility : Pattern violation (is neither PRIVATE nor PUBLIC)
        assertEquals(3, violations.size());
    }
}