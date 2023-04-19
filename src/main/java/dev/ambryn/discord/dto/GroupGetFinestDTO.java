package dev.ambryn.discord.dto;

import java.util.List;

public record GroupGetFinestDTO(
        Long id,
        String name,
        List<UserGetDTO> members) {}
