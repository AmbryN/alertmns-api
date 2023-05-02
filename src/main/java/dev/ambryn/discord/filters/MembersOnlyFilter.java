package dev.ambryn.discord.filters;

import dev.ambryn.discord.beans.Channel;
import dev.ambryn.discord.repositories.ChannelRepository;
import dev.ambryn.discord.repositories.UserRepository;
import dev.ambryn.discord.security.JwtUtils;
import jakarta.inject.Inject;
import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpointConfig;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Provider
@MembersOnly
public class MembersOnlyFilter extends ServerEndpointConfig.Configurator implements ContainerRequestFilter {

    Logger logger = LoggerFactory.getLogger("Members Only Filter");
    @Inject
    private JwtUtils jwt;
    @Inject
    private ChannelRepository channelRepository;
    @Inject
    private UserRepository userRepository;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        logger.debug("Verifying if user is member of channel");
        String[] path = requestContext.getUriInfo().getPath().split("/");
        long id;
        try {
            id = Long.parseLong(path[1]);
        } catch (NumberFormatException nfe) {
            throw new NotFoundException("Invalid id");
        }

        boolean isMemberOfChannel =
                Optional.ofNullable(requestContext.getHeaderString("Authorization"))
                        .map(jwt::getEmailFromBearer)
                        .flatMap(userRepository::getUserByEmail)
                        .flatMap(user -> channelRepository.getChannel(id)
                                .map(Channel::getMembers)
                                .map(users -> users.contains(user)))
                        .orElse(false);

        if (!isMemberOfChannel)
            throw new ForbiddenException("You are not a member of this channel");
    }

    @Override
    public void modifyHandshake(ServerEndpointConfig conf,
                                HandshakeRequest req,
                                HandshakeResponse resp) {
        System.out.println(resp.getHeaders().put("Sec-WebSocket-Accept", List.of("bla")));
    }

}
