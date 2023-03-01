package dev.ambryn.discordtest.controllers;

import dev.ambryn.discordtest.beans.Channel;
import dev.ambryn.discordtest.beans.Message;
import dev.ambryn.discordtest.dto.ChannelCreateDTO;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/channel")
public class ChannelController {

    @Inject
    ChannelRepository channelRepository;

    @GET
    @Path("/{id:[0-9]+}/messages")
    public Response getMessages(@PathParam("id") Long id) {
        Optional<List<Message>> oMessages = channelRepository.getMessages(id);

        return oMessages
                .flatMap(messages ->
                        Optional.of(messages.stream()
                                .map(MessageMapper::toDTO)
                                .collect(Collectors.toList())))
                .flatMap(dtos ->
                        Optional.ofNullable(Ok.build(dtos)))
                .orElseGet(() ->
                        NotFound.build("Could not find messages of channel with id=" + id)
                );
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createChannel(ChannelCreateDTO channelCreateDTO) {
        BeanValidator.validate(channelCreateDTO);

        Channel newChannel = ChannelMapper.toChannel(channelCreateDTO);

        return channelRepository
                .createChannel(newChannel)
                .flatMap(channel ->
                        Optional.of(
                                Created.build(ChannelMapper.toDTO(channel))
                        ))
                .orElseGet(() -> ServerError.build("Could not create channel"));
    }
}
