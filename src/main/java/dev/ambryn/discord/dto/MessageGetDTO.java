package dev.ambryn.discord.dto;

import dev.ambryn.discord.dto.user.UserGetDTO;

public record MessageGetDTO(
        Long id,
        UserGetDTO sender,
        String content,
        String fileName,
        String filePath) {}
