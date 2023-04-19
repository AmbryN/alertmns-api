package dev.ambryn.discord.services;

import dev.ambryn.discord.beans.User;
import jakarta.enterprise.context.ApplicationScoped;
import org.glassfish.soteria.identitystores.hash.Pbkdf2PasswordHashImpl;

@ApplicationScoped
public class CredentialService {
    private final Pbkdf2PasswordHashImpl hasher = new Pbkdf2PasswordHashImpl();

    public boolean verifyCredentials(User user, String password) {
        return  hasher.verify(password.toCharArray(), user.getPassword());
    }
}
