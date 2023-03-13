package dev.ambryn.discordtest.controllers;

import dev.ambryn.discordtest.beans.Channel;
import dev.ambryn.discordtest.beans.Message;
import dev.ambryn.discordtest.beans.User;
import dev.ambryn.discordtest.dto.ChannelCreateDTO;
import dev.ambryn.discordtest.dto.MessageCreateDTO;
import dev.ambryn.discordtest.enums.ERole;
import dev.ambryn.discordtest.filters.Authorize;
import dev.ambryn.discordtest.filters.MembersOnly;
import dev.ambryn.discordtest.repositories.UserRepository;
import dev.ambryn.discordtest.responses.Created;
import dev.ambryn.discordtest.responses.NotFound;
import dev.ambryn.discordtest.dto.mappers.dto.ChannelMapper;
import dev.ambryn.discordtest.dto.mappers.dto.MessageMapper;
import dev.ambryn.discordtest.repositories.ChannelRepository;
import dev.ambryn.discordtest.responses.Ok;
import dev.ambryn.discordtest.responses.ServerError;
import dev.ambryn.discordtest.validators.BeanValidator;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.stream.Collectors;

@Path("/channels")
public class ChannelController {

    @Inject
    ChannelRepository channelRepository;
    @Inject
    UserRepository userRepository;

    @GET
    @Path("/{id:[0-9]+}/messages")
    @Authorize(level = ERole.ADMIN)
    @MembersOnly
    public Response getMessages(@PathParam("id") Long id) {
        return channelRepository.getChannel(id)
                .map(Channel::getMessages)
                .map(messages ->
                        messages.stream()
                                .map(MessageMapper::toDTO)
                                .collect(Collectors.toList()))
                .map(Ok::build)
                .orElseGet(() ->
                        NotFound.build("Could not find messages of channel with id=" + id)
                );
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id:[0-9]+}/messages")
    public Response addMessage(@PathParam("id") Long id, MessageCreateDTO messageDTO) {
        BeanValidator.validate(messageDTO);
        Channel channel = channelRepository.getChannel(id).get();
        User user = userRepository.getUser(messageDTO.userId()).get();

        Message message = MessageMapper.toMessage(channel, user, messageDTO);
        channelRepository.addMessage(id, message);
                return Created.build(MessageMapper.toDTO(message));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createChannel(ChannelCreateDTO channelCreateDTO) {
        BeanValidator.validate(channelCreateDTO);

        Channel newChannel = ChannelMapper.toChannel(channelCreateDTO);

        return channelRepository
                .createChannel(newChannel)
                .map(ChannelMapper::toDTO)
                .map(Created::build)
                .orElseGet(() -> ServerError.build("Could not create channel"));
    }
}
