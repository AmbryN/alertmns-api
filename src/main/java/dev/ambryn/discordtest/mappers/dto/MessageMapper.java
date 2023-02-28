package dev.ambryn.discordtest.mappers.dto;

import dev.ambryn.discordtest.beans.Message;
import dev.ambryn.discordtest.beans.User;
import dev.ambryn.discordtest.dto.MessageGetDTO;
import dev.ambryn.discordtest.dto.UserGetDTO;

public class MessageMapper {

    public static MessageGetDTO toDTO(Message message) {
        User sender = message.getSender();
        UserGetDTO senderDTO = UserMapper.toDto(sender);
        return new MessageGetDTO(message.getId(), senderDTO, message.getContent());
    }
}
