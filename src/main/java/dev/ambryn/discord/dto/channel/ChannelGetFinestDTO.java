package dev.ambryn.discord.dto.channel;

import dev.ambryn.discord.dto.MessageGetDTO;
import dev.ambryn.discord.dto.user.UserGetDTO;

public record ChannelGetFinestDTO(
        Long id,
        String name,
        String visibility,
        MessageGetDTO[] messages,
        UserGetDTO[] members,
        UserGetDTO[] subscribers) {}
