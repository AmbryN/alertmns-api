package dev.ambryn.discordtest.dto.mappers.dto;

import dev.ambryn.discordtest.beans.Channel;
import dev.ambryn.discordtest.dto.ChannelCreateDTO;
import dev.ambryn.discordtest.dto.ChannelGetDTO;
import dev.ambryn.discordtest.enums.EVisibility;

public class ChannelMapper {

    public static Channel toChannel(ChannelCreateDTO channelDTO) {
        String name = channelDTO.name();
        EVisibility visibility = EVisibility.valueOf(channelDTO.visibility());
        return new Channel(name, visibility);
    }

    public static ChannelGetDTO toDTO(Channel channel) {
        Long id = channel.getId();
        String name = channel.getName();
        String visibility = channel.getVisibility().toString();
        return new ChannelGetDTO(id, name, visibility);
    }
}
