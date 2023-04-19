package dev.ambryn.discord.dto;

import dev.ambryn.discord.dto.channel.ChannelGetDTO;
import dev.ambryn.discord.dto.user.UserGetDTO;

public record NotificationDTO(
        Long id,
        ChannelGetDTO channel,
        UserGetDTO user,
        String type,
        Boolean seen) {}
