package dev.ambryn.discordtest.dto.mappers.dto;

import dev.ambryn.discordtest.beans.Channel;
import dev.ambryn.discordtest.beans.Message;
import dev.ambryn.discordtest.beans.User;
import dev.ambryn.discordtest.dto.MessageCreateDTO;
import dev.ambryn.discordtest.dto.MessageGetDTO;
import dev.ambryn.discordtest.dto.UserGetDTO;
import dev.ambryn.discordtest.repositories.ChannelRepository;
import dev.ambryn.discordtest.repositories.UserRepository;
import jakarta.inject.Inject;

public class MessageMapper {
    @Inject
    ChannelRepository channelRepository;

    @Inject
    UserRepository userRepository;

    public static MessageGetDTO toDTO(Message message) {
        User sender = message.getSender();
        UserGetDTO senderDTO = UserMapper.toDto(sender);
        return new MessageGetDTO(message.getId(), senderDTO, message.getContent(), null, null);
    }

    public static Message toMessage(Channel channel, User user, MessageCreateDTO dto) {
        return new Message(channel, user, dto.content());
    }
}
