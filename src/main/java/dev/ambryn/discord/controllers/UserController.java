package dev.ambryn.discord.controllers;

import dev.ambryn.discord.beans.User;
import dev.ambryn.discord.dto.RoleDTO;
import dev.ambryn.discord.dto.mappers.dto.ChannelMapper;
import dev.ambryn.discord.dto.user.UserCreateDTO;
import dev.ambryn.discord.dto.user.UserGetDTO;
import dev.ambryn.discord.dto.mappers.dto.UserMapper;
import dev.ambryn.discord.enums.ERole;
import dev.ambryn.discord.errors.DataAccessException;
import dev.ambryn.discord.filters.Authorize;
import dev.ambryn.discord.repositories.RoleRepository;
import dev.ambryn.discord.repositories.UserRepository;
import dev.ambryn.discord.responses.Created;
import dev.ambryn.discord.responses.NotFound;
import dev.ambryn.discord.responses.Ok;
import dev.ambryn.discord.responses.ServerError;
import dev.ambryn.discord.security.JwtUtils;
import dev.ambryn.discord.validators.BeanValidator;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
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
    @Inject
    JwtUtils jwtUtils;

    @GET
    @Produces(value = "application/json")
    public Response getUsers() {
        logger.debug("Getting all users");
        List<UserGetDTO> users =  userRepository.getUsers()
                .stream()
                .map(UserMapper::toDto)
                .toList();
        return Ok.build(users);
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

    @GET
    @Path("/profile")
    @Produces(APPLICATION_JSON)
    public Response getProfile(@HeaderParam("Authorization") String token) {
        logger.debug("Getting user profile");
        String jwt = jwtUtils.extractJwtFromHeader(token);
        return userRepository.getUserByEmail(jwtUtils.getEmailFromToken(jwt))
                .map(UserMapper::toFinestDto)
                .map(Ok::build)
                .orElseGet(() -> NotFound.build("Could not find user corresponding to Jwt"));
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
    public Response addRole(@PathParam("id") Long id, RoleDTO roleToAdd) {
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
                                                throw new NotFoundException("Could not find role with id=" + roleToAdd.id());
                                            });
                        },
                        () -> {
                            throw new NotFoundException("Could not find user with id=" + id);
                        });

        return Ok.build();
    }
}
