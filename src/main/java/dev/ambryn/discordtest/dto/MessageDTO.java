package dev.ambryn.discordtest.dto;

import jakarta.validation.constraints.*;

public record MessageDTO(
        @NotNull(message = "ne peut être vide")
        @Digits(message = "doit être un entier positif", integer = Integer.SIZE, fraction = 0)
        @Positive
        Long id,

        @NotNull(message = "ne peut être vide")
        @Digits(message = "doit être un entier positif", integer = Integer.SIZE, fraction = 0)
        @Positive
        Long userId,

        @NotNull(message = "ne peut être vide")
        @NotBlank
        @Size(min = 1, max = 200, message = "doit contenir entre 1 et 200 caractères")
        String content) {}
