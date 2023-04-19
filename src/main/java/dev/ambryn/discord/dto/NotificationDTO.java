package dev.ambryn.discord.dto;

public record NotificationDTO(
        Long id,
        ChannelGetDTO channel,
        UserGetDTO user,
        String type,
        Boolean seen) {}
