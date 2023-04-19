package dev.ambryn.discord.dto;

import dev.ambryn.discord.dto.user.UserGetDTO;

import java.util.List;

public record GroupGetFinestDTO(
        Long id,
        String name,
        List<UserGetDTO> members) {}
