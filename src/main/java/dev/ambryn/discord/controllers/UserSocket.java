package dev.ambryn.discord.controllers;

import dev.ambryn.discord.beans.Channel;
import dev.ambryn.discord.beans.Message;
import dev.ambryn.discord.beans.User;
import dev.ambryn.discord.dto.MessageCreateDTO;
import dev.ambryn.discord.filters.MembersOnly;
import dev.ambryn.discord.filters.MembersOnlyFilter;
import dev.ambryn.discord.repositories.ChannelRepository;
import dev.ambryn.discord.repositories.UserRepository;
import dev.ambryn.discord.services.AuthorizationService;
import dev.ambryn.discord.validators.BeanValidator;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

@ServerEndpoint(value = "/socket/{id}",
encoders = {MessageEncoder.class},
decoders = {MessageDecoder.class},
        configurator = MembersOnlyFilter.class)
public class UserSocket {

    Logger logger = LoggerFactory.getLogger("Socket");
    @Inject
    private UserRepository userRepository;
    @Inject
    private ChannelRepository channelRepository;

    private Long channelId;

    public UserSocket() {}
    @OnOpen
    public void open(Session session, EndpointConfig conf, @PathParam("id") Long id, @PathParam("token") String token) {
        logger.debug("Opening socket on channel with id=" + id);
        this.channelId = id;
//        sendAll(session, "{ \"content\": \"Open\" }");
    }
    @OnMessage
    @MembersOnly
    public void message(Session session, MessageCreateDTO messageCreateDTO) {
        logger.debug("Receiving message");
        try {
            BeanValidator.validate(messageCreateDTO);
        } catch (ConstraintViolationException e) {
            System.out.println("Le format du message est incorrect");
        }

        Optional<User> userOption = userRepository.getUser(messageCreateDTO.userId());
        System.out.println(userOption);
        Optional<Channel> channelOption = channelRepository.getChannel(channelId);
        System.out.println(channelOption);

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
