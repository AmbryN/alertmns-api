package dev.ambryn.discord.dto.mappers.dto;

import dev.ambryn.discord.beans.Channel;
import dev.ambryn.discord.dto.channel.ChannelCreateDTO;
import dev.ambryn.discord.dto.channel.ChannelGetDTO;
import dev.ambryn.discord.enums.EVisibility;

public class ChannelMapper {

    public static Channel toChannel(ChannelCreateDTO channelDTO) {
        String name = channelDTO.name();
        EVisibility visibility = channelDTO.visibility();
        return new Channel(name, visibility);
    }

    public static ChannelGetDTO toDTO(Channel channel) {
        Long id = channel.getId();
        String name = channel.getName();
        String visibility = channel.getVisibility().toString();
        return new ChannelGetDTO(id, name, visibility);
    }
}
