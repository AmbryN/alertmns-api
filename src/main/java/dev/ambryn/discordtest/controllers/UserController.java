package dev.ambryn.discordtest.controllers;

import dev.ambryn.discordtest.beans.Role;
import dev.ambryn.discordtest.beans.User;
import dev.ambryn.discordtest.dto.RoleAddDTO;
import dev.ambryn.discordtest.dto.UserCreateDTO;
import dev.ambryn.discordtest.dto.UserGetDTO;
import dev.ambryn.discordtest.dto.mappers.dto.UserMapper;
import dev.ambryn.discordtest.enums.ERole;
import dev.ambryn.discordtest.errors.DataAccessException;
import dev.ambryn.discordtest.filters.Authorize;
import dev.ambryn.discordtest.repositories.RoleRepository;
import dev.ambryn.discordtest.repositories.UserRepository;
import dev.ambryn.discordtest.responses.Created;
import dev.ambryn.discordtest.responses.NotFound;
import dev.ambryn.discordtest.responses.Ok;
import dev.ambryn.discordtest.responses.ServerError;
import dev.ambryn.discordtest.validators.BeanValidator;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/users")
@Authorize
public class UserController {

    Logger logger = LoggerFactory.getLogger("UserController");
    @Inject
    UserRepository userRepository;
    @Inject
    RoleRepository roleRepository;

    @GET
    @Produces(value = "application/json")
    public Response getUsers() {
        logger.debug("Getting all users");
        List<UserGetDTO> users =  userRepository.getUsers()
                .stream()
                .map(UserMapper::toDto)
                .toList();
        return Response.ok(users).build();
    }

    @GET
    @Path("/{id: [0-9]+}")
    @Produces(APPLICATION_JSON)
    @Authorize(level = ERole.ADMIN)
    public Response getUser(@PathParam("id") Long id) {
        logger.debug("Getting user with id={}", id);
        return userRepository.getUser(id)
                .map(UserMapper::toFinestDto)
                .map(Ok::build)
                .orElseGet(() -> NotFound.build("Could not find user with id=" + id));
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @Authorize(level = ERole.ADMIN)
    public Response saveUser(UserCreateDTO userDTO) {
        logger.debug("Saving user={}", userDTO);
        BeanValidator.validate(userDTO);

        User user = UserMapper.toUser(userDTO);
        try {
            userRepository.saveUser(user);
            return Created.build(UserMapper.toDto(user));
        } catch (DataAccessException dae) {
            return ServerError.build(dae.getMessage());
        }
    }

    @POST
    @Path("/{id: [0-9]+}/roles")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @Authorize(level = ERole.ADMIN)
    public Response addRole(@PathParam("id") Long id, RoleAddDTO roleToAdd) {
        logger.debug("Add role {} to user with id={}", roleToAdd, id);
        BeanValidator.validate(roleToAdd);

        userRepository.getUser(id)
                .ifPresentOrElse(user -> {
                            roleRepository.getRole(roleToAdd.id())
                                    .ifPresentOrElse(role -> {
                                        user.addRole(role);
                                        userRepository.saveUser(user);
                                    },
                                            () -> {
                                                throw new NotFoundException("Could not find role with id=" + roleToAdd);
                                            });
                        },
                        () -> {
                            throw new NotFoundException("Could not find user with id=" + id);
                        });

        return Ok.build();
    }
}
