package dev.ambryn.discordtest.controllers;

import dev.ambryn.discordtest.beans.Message;
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
