package dev.ambryn.discordtest.dto;

import java.util.List;

public record GroupGetFinestDTO(
        Long id,
        String name,
        List<UserGetDTO> members) {}
