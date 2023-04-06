package dev.ambryn.discordtest.controllers;

import dev.ambryn.discordtest.beans.User;
import dev.ambryn.discordtest.dto.UserGetDTO;
import dev.ambryn.discordtest.dto.UserCreateDTO;
import dev.ambryn.discordtest.dto.mappers.dto.UserMapper;
import dev.ambryn.discordtest.enums.ERole;
import dev.ambryn.discordtest.filters.Authorize;
import dev.ambryn.discordtest.repositories.UserRepository;
import dev.ambryn.discordtest.responses.Conflict;
import dev.ambryn.discordtest.responses.NotFound;
import dev.ambryn.discordtest.responses.Ok;
import dev.ambryn.discordtest.validators.BeanValidator;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/users")
public class UserController {

    Logger logger = LoggerFactory.getLogger("UserController");
    @Inject
    UserRepository userRepository;

    @GET
    @Produces(value = "application/json")
    @Authorize(level = ERole.ADMIN)
    public List<UserGetDTO> getUsers() {
        logger.debug("Getting all users");
        return userRepository.getUsers()
                .stream()
                .map(UserMapper::toDto)
                .toList();
    }

    @GET
    @Path("/{id: [0-9]+}")
    @Produces(APPLICATION_JSON)
    public Response getUser(@PathParam("id") Long id) {
        logger.debug("Getting user with id={}", id);
        return userRepository.getUser(id)
                .map(UserMapper::toDto)
                .map(Ok::build)
                .orElseGet(() -> NotFound.build("Could not find user with id=" + id));
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response postUser(UserCreateDTO userDTO) {
        logger.debug("Creating new user from DTO {}", userDTO);
        BeanValidator.validate(userDTO);

        return userRepository.getUserByEmail(userDTO.email())
                .map(user -> Conflict.build("User with email " + userDTO.email() + " already exists"))
                .orElseGet(() -> {
                    User user = UserMapper.toUser(userDTO);
                    userRepository.addUser(user);
                    return Ok.build(UserMapper.toDto(user));
                });
    }
}
