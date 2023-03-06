package dev.ambryn.discordtest.validators;

import dev.ambryn.discordtest.dto.MeetingCreateDTO;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MeetingValidationTest {

    @Test
    void computeViolationsShouldContainNoneIfPassedAValidDTO() {
        MeetingCreateDTO dto = new MeetingCreateDTO(1L, "My Name", Timestamp.from(Instant.now().plus(Duration.ofMillis(1000))), 120);
        Set<ConstraintViolation<MeetingCreateDTO>> violations = BeanValidator.computeViolations(dto);
        assertEquals(0, violations.size());
    }

    @Test
    void computeViolationsShouldContainViolations() {
        MeetingCreateDTO dto = new MeetingCreateDTO(null, "", Timestamp.from(Instant.now()), 0);
        Set<ConstraintViolation<MeetingCreateDTO>> violations = BeanValidator.computeViolations(dto);
        // Should contain
        // OrganizerId: Not null
        // Name: Should not be empty, Size violation (less than 2 characters)
        // Datetime: Should be a date in Present or Future
        // Duration: Should be positive
        assertEquals(5, violations.size());
    }
}