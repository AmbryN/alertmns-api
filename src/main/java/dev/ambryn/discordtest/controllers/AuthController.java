package dev.ambryn.discordtest.controllers;

import dev.ambryn.discordtest.dto.JwtResponse;
import dev.ambryn.discordtest.dto.LoginDTO;
import dev.ambryn.discordtest.repositories.UserRepository;
import dev.ambryn.discordtest.responses.Ok;
import dev.ambryn.discordtest.responses.Unauthorized;
import dev.ambryn.discordtest.security.JwtUtils;
import dev.ambryn.discordtest.validators.BeanValidator;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.soteria.identitystores.hash.Pbkdf2PasswordHashImpl;

import java.util.Optional;

@Path("/login")
public class AuthController {
    @Inject
    UserRepository userRepository;

    @Inject
    JwtUtils jwt;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(LoginDTO login) {
        BeanValidator.validate(login);

        Pbkdf2PasswordHashImpl hasher = new Pbkdf2PasswordHashImpl();

        return userRepository.getUserByEmail(login.email())
                .flatMap(user -> {
                    boolean validPassword = hasher.verify(login.password().toCharArray(), user.getPassword());
                    if (validPassword) return Optional.of(user);
                    return Optional.empty();
                }).map(user -> {
                    String token = jwt.generateJwtToken(user);
                    return Ok.build(new JwtResponse(token, user.getId(), user.getEmail()));
                }).orElseGet(() -> Unauthorized.build("Email and/or password are incorrect"));
    }
}
