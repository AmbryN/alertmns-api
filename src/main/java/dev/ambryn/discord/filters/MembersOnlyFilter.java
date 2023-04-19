package dev.ambryn.discord.filters;

import dev.ambryn.discord.beans.Channel;
import dev.ambryn.discord.repositories.ChannelRepository;
import dev.ambryn.discord.repositories.UserRepository;
import dev.ambryn.discord.security.JwtUtils;
import jakarta.inject.Inject;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.util.Optional;

@Provider
@MembersOnly
public class MembersOnlyFilter implements ContainerRequestFilter {
    @Inject
    private JwtUtils jwt;
    @Inject
    private ChannelRepository channelRepository;
    @Inject
    private UserRepository userRepository;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String[] path = requestContext.getUriInfo().getPath().split("/");
        long id;
        try {
            id = Long.parseLong(path[1]);
        } catch (NumberFormatException nfe) {
            throw new NotFoundException("Invalid id");
        }

        boolean isMemberOfChannel =
                Optional.ofNullable(requestContext.getHeaderString("Authorization"))
                        .map(jwt::getEmailFromToken)
                        .flatMap(userRepository::getUserByEmail)
                        .flatMap(user -> channelRepository.getChannel(id)
                                .map(Channel::getMembers)
                                .map(users -> users.contains(user)))
                        .orElse(false);

        if (!isMemberOfChannel)
            throw new ForbiddenException("You are not a member of this channel");
    }
}
