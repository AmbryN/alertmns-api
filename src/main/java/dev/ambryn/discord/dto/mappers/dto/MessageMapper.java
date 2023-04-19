package dev.ambryn.discord.dto.mappers.dto;

import dev.ambryn.discord.beans.Channel;
import dev.ambryn.discord.beans.Message;
import dev.ambryn.discord.beans.User;
import dev.ambryn.discord.dto.MessageCreateDTO;
import dev.ambryn.discord.dto.MessageGetDTO;
import dev.ambryn.discord.dto.user.UserGetDTO;
import dev.ambryn.discord.repositories.ChannelRepository;
import dev.ambryn.discord.repositories.UserRepository;
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
