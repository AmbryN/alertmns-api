package dev.ambryn.discord.services;

import dev.ambryn.discord.beans.Role;
import dev.ambryn.discord.beans.User;
import dev.ambryn.discord.enums.ERole;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AuthorizationService {
    public boolean isAdmin(User user) {
        return user.getRoles().stream().map(Role::getName).toList().contains(ERole.ADMIN);
    }
}
