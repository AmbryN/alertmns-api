package dev.ambryn.discordtest.dto;

import jakarta.validation.constraints.*;

public record ChannelCreateDTO(
        @NotNull(message = "ne peut être vide")
        @NotBlank
        @Size(min = 1, max = 50, message = "doit contenir entre 1 et 50 caractères")
        String name) {}
