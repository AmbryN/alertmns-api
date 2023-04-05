package dev.ambryn.discordtest.controllers;

import dev.ambryn.discordtest.dto.LoginDTO;
import dev.ambryn.discordtest.repositories.UserRepository;
import dev.ambryn.discordtest.responses.Ok;
import dev.ambryn.discordtest.responses.Unauthorized;
import dev.ambryn.discordtest.security.JwtUtils;
import dev.ambryn.discordtest.services.CredentialService;
import dev.ambryn.discordtest.validators.BeanValidator;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/login")
public class AuthController {
    @Inject
    UserRepository userRepository;
    @Inject
    CredentialService credentialService;

    Logger logger = LoggerFactory.getLogger("UserController");

    @Inject
    JwtUtils jwt;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(LoginDTO login) {
        BeanValidator.validate(login);
        logger.debug("Try to login with email=" + login.email());

        return userRepository.getUserByEmail(login.email())
                .filter(user -> credentialService.verifyCredentials(user, login.password()))
                .map(user -> Ok.build(jwt.generateJwtToken(user)))
                .orElseGet(() -> Unauthorized.build("Email and/or password is/are incorrect"));
    }
}
