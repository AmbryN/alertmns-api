package dev.ambryn.discordtest.dto;

public record ChannelGetFinestDTO(
        Long id,
        String name,
        String visibility,
        MessageGetDTO[] messages,
        UserGetDTO[] members,
        UserGetDTO[] subscribers) {}
