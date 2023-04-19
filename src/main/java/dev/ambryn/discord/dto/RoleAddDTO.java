package dev.ambryn.discord.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RoleAddDTO(
        @NotNull
        @Digits(message = "doit être un entier positif", integer = Integer.SIZE, fraction = 0)
        @Positive(message = "doit être supérieur à 0")
        Long id
) {}
