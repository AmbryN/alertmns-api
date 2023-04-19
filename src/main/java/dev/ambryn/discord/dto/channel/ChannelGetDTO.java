package dev.ambryn.discord.dto.channel;

public record ChannelGetDTO(
        Long id,
        String name,
        String visibility) {}
