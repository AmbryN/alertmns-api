package dev.ambryn.discordtest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ChannelGetDTO(
        Long id,
        String name,
        String visibility) {}
