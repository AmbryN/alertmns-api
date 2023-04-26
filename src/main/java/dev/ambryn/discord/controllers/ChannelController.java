package dev.ambryn.discord.controllers;

import dev.ambryn.discord.beans.Channel;
import dev.ambryn.discord.beans.User;
import dev.ambryn.discord.dto.channel.ChannelCreateDTO;
import dev.ambryn.discord.dto.channel.ChannelGetDTO;
import dev.ambryn.discord.dto.mappers.dto.ChannelMapper;
import dev.ambryn.discord.dto.mappers.dto.MessageMapper;
import dev.ambryn.discord.enums.EVisibility;
import dev.ambryn.discord.errors.DataAccessException;
import dev.ambryn.discord.filters.Authorize;
import dev.ambryn.discord.filters.MembersOnly;
import dev.ambryn.discord.repositories.ChannelRepository;
import dev.ambryn.discord.repositories.UserRepository;
import dev.ambryn.discord.responses.*;
import dev.ambryn.discord.security.JwtUtils;
import dev.ambryn.discord.services.AuthorizationService;
import dev.ambryn.discord.validators.BeanValidator;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/channels")
@Authorize
public class ChannelController {

    @Inject
    ChannelRepository channelRepository;
    @Inject
    UserRepository userRepository;
    @Inject
    JwtUtils jwtUtils;
    @Inject
    AuthorizationService authorizationService;

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

    /**
     * Allows for the creation of a new channel if the authorized user has ADMIN privileges
     * OR if the user does not have ADMIN privileges but creates a PRIVATE channel.
     * @param bearer JSON Web Token
     * @param channelCreateDTO the channel to create
     * @return HTTP Response
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createChannel(@HeaderParam("Authorization") String bearer, ChannelCreateDTO channelCreateDTO) {
        BeanValidator.validate(channelCreateDTO);

        String email = jwtUtils.getEmailFromToken(bearer);
        Optional<User> oUser = userRepository.getUserByEmail(email);

        if (oUser.isPresent()) {
            User user = oUser.get();
            boolean isAdmin = authorizationService.isAdmin(user);

            if (isAdmin || channelCreateDTO.visibility() == EVisibility.PRIVATE) {
                Channel newChannel = ChannelMapper.toChannel(channelCreateDTO);

                try {
                    channelRepository.saveChannel(newChannel);
                    return Created.build(ChannelMapper.toDTO(newChannel));
                } catch (DataAccessException dae) {
                    return ServerError.build(dae.getMessage());
                }
            }
        }
        return Forbidden.build();
    }
}
