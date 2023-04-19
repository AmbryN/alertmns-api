package dev.ambryn.discord.controllers;

import dev.ambryn.discord.beans.Channel;
import dev.ambryn.discord.beans.Message;
import dev.ambryn.discord.beans.User;
import dev.ambryn.discord.dto.MessageCreateDTO;
import dev.ambryn.discord.repositories.ChannelRepository;
import dev.ambryn.discord.repositories.UserRepository;
import dev.ambryn.discord.validators.BeanValidator;
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
    private ChannelRepository channelRepository;

    public UserSocket() {}
    @OnOpen
    public void open(Session session) {
//        sendAll(session, "{ \"content\": \"Open\" }");
    }
    @OnMessage
    public void message(Session session, MessageCreateDTO messageCreateDTO) {
        try {
            BeanValidator.validate(messageCreateDTO);
        } catch (ConstraintViolationException e) {
            System.out.println("Le format du message est incorrect");
        }

        Optional<User> userOption = userRepository.getUser(messageCreateDTO.userId());
        Optional<Channel> channelOption = channelRepository.getChannel(1L);

        if (userOption.isPresent() && channelOption.isPresent()) {
            Channel channel = channelOption.get();
            User user = userOption.get();
            String content = messageCreateDTO.content();

            Message message = new Message();
            message.setChannel(channel);
            message.setSender(user);
            message.setContent(content);

            channel.addMessage(message);
            channelRepository.saveChannel(channel);

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
