package dev.ambryn.discord.services;

import dev.ambryn.discord.beans.Channel;
import dev.ambryn.discord.beans.Role;
import dev.ambryn.discord.beans.User;
import dev.ambryn.discord.enums.ERole;
import dev.ambryn.discord.repositories.ChannelRepository;
import dev.ambryn.discord.repositories.UserRepository;
import dev.ambryn.discord.security.JwtUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AuthorizationService {
    @Inject
    UserRepository userRepository;

    @Inject
    ChannelRepository channelRepository;

    @Inject
    JwtUtils jwtUtils;

    public boolean isAdmin(User user) {
        return user.getRoles().stream().map(Role::getName).toList().contains(ERole.ADMIN);
    }

    public boolean isMember(String jwt, Long channelId) {
        return userRepository.getUserByEmail(jwtUtils.getEmailFromToken(jwt))
                .flatMap(user -> channelRepository.getChannel(channelId)
                        .map(Channel::getMembers)
                        .map(users -> users.contains(user)))
                .orElse(false);
    }
}
