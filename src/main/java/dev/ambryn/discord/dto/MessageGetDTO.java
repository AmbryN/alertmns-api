package dev.ambryn.discord.dto;

public record MessageGetDTO(
        Long id,
        UserGetDTO sender,
        String content,
        String fileName,
        String filePath) {}
