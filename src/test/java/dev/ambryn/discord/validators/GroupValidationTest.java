package dev.ambryn.discord.validators;

import dev.ambryn.discord.dto.group.GroupCreateDTO;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GroupValidationTest {

    @Test
    void computeViolationsShouldContainNoneIfPassedAValidDTO() {
        GroupCreateDTO dto = new GroupCreateDTO("My Name");
        Set<ConstraintViolation<GroupCreateDTO>> violations = BeanValidator.computeViolations(dto);
        assertEquals(0, violations.size());
    }

    @Test
    void computeViolationsShouldContainViolations() {
        GroupCreateDTO dto = new GroupCreateDTO(null);
        Set<ConstraintViolation<GroupCreateDTO>> violations = BeanValidator.computeViolations(dto);
        // Should contain
        // Name: Not empty, Length violation (less than 2 characters)
        assertEquals(2, violations.size());
    }
}