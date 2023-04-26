package dev.ambryn.discord.validators;

import dev.ambryn.discord.dto.channel.ChannelCreateDTO;
import dev.ambryn.discord.enums.EVisibility;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChannelValidationTest {

    @Test
    void computeViolationsShouldContainNoneIfPassedAValidDTO() {
        ChannelCreateDTO dto = new ChannelCreateDTO("My Channel", EVisibility.PUBLIC);
        Set<ConstraintViolation<ChannelCreateDTO>> violations = BeanValidator.computeViolations(dto);
        assertEquals(0, violations.size());
    }

    @Test
    void computeViolationsShouldContainViolations() {
        ChannelCreateDTO dto = new ChannelCreateDTO("", EVisibility.PRIVATE);
        Set<ConstraintViolation<ChannelCreateDTO>> violations = BeanValidator.computeViolations(dto);
        // Should contain
        // Name : Should not be empty, Size violation (less than 2 characters)
        // Visibility : Pattern violation (is neither PRIVATE nor PUBLIC)
        assertEquals(2, violations.size());
    }
}