package dev.ambryn.discord.controllers;

import dev.ambryn.discord.beans.Message;
import jakarta.websocket.*;

public class MessageEncoder implements Encoder.Text<Message> {

    @Override
    public String encode(Message message) throws EncodeException {
        return "{\"content\": \"" + message.getContent() + "\"}";
    }

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }
}
