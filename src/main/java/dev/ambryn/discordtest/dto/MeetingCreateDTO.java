package dev.ambryn.discordtest.dto;

import jakarta.validation.constraints.*;

import java.sql.Timestamp;

public record MeetingCreateDTO(
        @NotNull(message = "ne peut être vide")
        @Digits(message = "doit être un entier", integer = Integer.SIZE, fraction = 0)
        @Positive(message = "doit être positif")
        Long organizerId,

        @NotNull(message = "ne peut être vide")
        @FutureOrPresent
        Timestamp datetime,

        @NotNull(message = "ne peut être vide")
        @Digits(integer = 1440, fraction = 0, message = "doit être exprimé en minutes avec un maximum de 1440 minutes (24h)")
        @Positive(message = "doit être positif")
        int duration) {}
