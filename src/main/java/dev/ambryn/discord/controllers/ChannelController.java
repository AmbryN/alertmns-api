package dev.ambryn.discord.controllers;

import dev.ambryn.discord.beans.Channel;
import dev.ambryn.discord.dto.channel.ChannelCreateDTO;
import dev.ambryn.discord.dto.channel.ChannelGetDTO;
import dev.ambryn.discord.enums.ERole;
import dev.ambryn.discord.errors.DataAccessException;
import dev.ambryn.discord.filters.Authorize;
import dev.ambryn.discord.filters.MembersOnly;
import dev.ambryn.discord.responses.Created;
import dev.ambryn.discord.responses.NotFound;
import dev.ambryn.discord.dto.mappers.dto.ChannelMapper;
import dev.ambryn.discord.dto.mappers.dto.MessageMapper;
import dev.ambryn.discord.repositories.ChannelRepository;
import dev.ambryn.discord.responses.Ok;
import dev.ambryn.discord.responses.ServerError;
import dev.ambryn.discord.validators.BeanValidator;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/channels")
@Authorize
public class ChannelController {

    @Inject
    ChannelRepository channelRepository;

    @GET
    public Response getChannels() {
        List<ChannelGetDTO> channels = channelRepository.getChannels()
                .stream().map(ChannelMapper::toDTO)
                .toList();
        return Ok.build(channels);
    }

    @GET
    @Path("/{id:[0-9]+}")
    public Response getChannel(@PathParam("id") Long id) {
        return channelRepository.getChannel(id)
                .map(ChannelMapper::toDTO)
                .map(Ok::build)
                .orElseGet(() ->
                        NotFound.build("Could not find channel with id=" + id)
                );
    }

    @GET
    @Path("/{id:[0-9]+}/messages")
    @MembersOnly
    public Response getMessages(@PathParam("id") Long id) {
        return channelRepository.getChannel(id)
                .map(Channel::getMessages)
                .map(messages ->
                        messages.stream()
                                .map(MessageMapper::toDTO)
                                .toList()
                )
                .map(Ok::build)
                .orElseGet(() ->
                        NotFound.build("Could not find messages of channel with id=" + id)
                );
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Authorize(level = ERole.ADMIN)
    public Response createChannel(ChannelCreateDTO channelCreateDTO) {
        BeanValidator.validate(channelCreateDTO);

        Channel newChannel = ChannelMapper.toChannel(channelCreateDTO);

        Response response = null;
        try {
            channelRepository.saveChannel(newChannel);
            response = Created.build(ChannelMapper.toDTO(newChannel));
        } catch (DataAccessException dae) {
            response = ServerError.build(dae.getMessage());
        }
        return response;
    }
}
