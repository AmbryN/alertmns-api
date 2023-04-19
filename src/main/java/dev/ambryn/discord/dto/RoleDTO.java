package dev.ambryn.discord.dto;

import dev.ambryn.discord.enums.ERole;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RoleDTO(
        @NotNull
        @Digits(message = "doit être un entier positif", integer = Integer.SIZE, fraction = 0)
        @Positive(message = "doit être supérieur à 0")
        Long id,

        ERole name
) {}
