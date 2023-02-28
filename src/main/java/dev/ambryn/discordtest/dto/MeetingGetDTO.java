package dev.ambryn.discordtest.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.sql.Timestamp;

public record MeetingGetDTO(
        Long id,
        UserGetDTO organizer,
        Timestamp datetime,
        int duration) {}
