package dev.ambryn.discordtest.mappers.dto;

import dev.ambryn.discordtest.beans.Message;
import dev.ambryn.discordtest.dto.MessageDTO;

public class MessageMapper {
    public static Message toMessage(MessageDTO messageDTO) {
        Message message = new Message();
//        message.setSender(messageDTO.id());
        message.setContent(messageDTO.content());

        return message;
    }

    public static MessageDTO toDTO(Message message) {
        return new MessageDTO(message.getId(), message.getContent());
    }
}
