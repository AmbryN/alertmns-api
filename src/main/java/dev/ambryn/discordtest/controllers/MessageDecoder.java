package dev.ambryn.discordtest.controllers;

import dev.ambryn.discordtest.dto.MessageCreateDTO;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.websocket.DecodeException;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;


public class MessageDecoder implements Decoder.Text<MessageCreateDTO> {
    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {

    }

    @Override
    public MessageCreateDTO decode(String s) throws DecodeException {
        try (Jsonb jsonb = JsonbBuilder.create()) {
            return jsonb.fromJson(s, MessageCreateDTO.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean willDecode(String s) {
        return true;
    }
}
