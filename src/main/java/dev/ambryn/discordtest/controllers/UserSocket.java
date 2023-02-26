package dev.ambryn.discordtest.controllers;

import dev.ambryn.discordtest.beans.Channel;
import dev.ambryn.discordtest.beans.Message;
import dev.ambryn.discordtest.beans.User;
import dev.ambryn.discordtest.dto.MessageDTO;
import dev.ambryn.discordtest.repositories.ChannelRepository;
import dev.ambryn.discordtest.repositories.MessageRepository;
import dev.ambryn.discordtest.repositories.UserRepository;
import dev.ambryn.discordtest.validators.BeanValidator;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.websocket.EncodeException;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.Optional;

@ServerEndpoint(value = "/socket",
encoders = {MessageEncoder.class},
decoders = {MessageDecoder.class})
public class UserSocket {

    @Inject
    private UserRepository userRepository;
    @Inject
    private MessageRepository messageRepository;
    @Inject
    private ChannelRepository channelRepository;

    public UserSocket() {}
    @OnOpen
    public void open(Session session) {
//        sendAll(session, "{ \"content\": \"Open\" }");
    }
    @OnMessage
    public void message(Session session, MessageDTO messageDTO) {
        try {
            BeanValidator.validate(messageDTO);
        } catch (ConstraintViolationException e) {
            throw  e;
        }

        Optional<User> userOption = userRepository.getUser(messageDTO.userId());
        Optional<Channel> channelOptional = channelRepository.getChannel(1L);

        if (userOption.isPresent() && channelOptional.isPresent()) {
            Message message = new Message(channelOptional.get(), userOption.get(), messageDTO.content());

            Channel channel = channelOptional.get();
            channel.addMessage(message);
            channelRepository.updateChannel(channel);
            System.out.println(channel.getMessages());
            sendAll(session, message);
        }
    }

    public void sendAll(Session session, Message message) {
        for (Session sess : session.getOpenSessions()) {
            try {
                sess.getBasicRemote().sendObject(message);
            } catch (IOException | EncodeException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
