package dev.ambryn.discordtest.dto;

public record GroupGetFinestDTO(
        Long id,
        String name,
        UserGetDTO[] members) {}
