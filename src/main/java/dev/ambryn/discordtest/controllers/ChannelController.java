package dev.ambryn.discordtest.controllers;

import dev.ambryn.discordtest.beans.Channel;
import dev.ambryn.discordtest.dto.ChannelCreateDTO;
import dev.ambryn.discordtest.enums.ERole;
import dev.ambryn.discordtest.errors.DataAccessException;
import dev.ambryn.discordtest.filters.Authorize;
import dev.ambryn.discordtest.filters.MembersOnly;
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

@Path("/channels")
@Authorize
public class ChannelController {

    @Inject
    ChannelRepository channelRepository;

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
