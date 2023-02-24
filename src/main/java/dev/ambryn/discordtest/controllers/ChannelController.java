package dev.ambryn.discordtest.controllers;

import dev.ambryn.discordtest.beans.Message;
import dev.ambryn.discordtest.dto.MessageDTO;
import dev.ambryn.discordtest.mappers.dto.MessageMapper;
import dev.ambryn.discordtest.repositories.ChannelRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

@Path("/channel")
public class ChannelController {

    @Inject
    ChannelRepository channelRepository;

    @GET
    @Path("/{id:[0-9]+}/messages")
    public Response getMessages(@PathParam("id") Long id) {
        List<Message> messages = channelRepository.getMessages(id);
        List<MessageDTO> messageDTOs = new ArrayList<>();
        for (Message message : messages) {
            MessageDTO dto = MessageMapper.toDTO(message);
            messageDTOs.add(dto);
        }
        return Response.ok().header("Access-Control-Allow-Origin", "*").entity(messageDTOs).build();
    }
}
