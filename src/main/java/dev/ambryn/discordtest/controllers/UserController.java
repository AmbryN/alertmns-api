package dev.ambryn.discordtest.controllers;

import dev.ambryn.discordtest.beans.User;
import dev.ambryn.discordtest.dto.UserGetDTO;
import dev.ambryn.discordtest.dto.UserCreateDTO;
import dev.ambryn.discordtest.errors.ErrorMessage;
import dev.ambryn.discordtest.dto.mappers.dto.UserMapper;
import dev.ambryn.discordtest.repositories.UserRepository;
import dev.ambryn.discordtest.validators.BeanValidator;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/users")
public class UserController {

    @Inject
    UserRepository userRepository;

    @GET
    @Produces(value = "application/json")
    public List<UserGetDTO> getUsers() throws ClassNotFoundException {
        List<UserGetDTO> usersDTO = new ArrayList<>();
        for (User user : userRepository.getUsers()) {
            usersDTO.add(UserMapper.toDto(user));
        }
        return usersDTO;
    }

    @GET
    @Path("/{id: [0-9]+}")
    @Produces(APPLICATION_JSON)
    public Response getUser(@PathParam("id") Long id) {
        Optional<User> userOption = userRepository.getUser(id);
        if (userOption.isPresent()) {
            User user = userOption.get();
            UserGetDTO userGetDTO = UserMapper.toDto(user);
            return Response.ok(userGetDTO).build();
        }
        return Response.status(404).entity(new ErrorMessage(1000, "Could not find user with id=" + id)).build();
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response postUser(UserCreateDTO userDTO) {
        BeanValidator.validate(userDTO);

        if (userRepository.getUserByEmail(userDTO.email()).isPresent()) {
            return Response.status(409).entity(new ErrorMessage(4009, "User with email " + userDTO.email() + " already exists")).build();
        }
        try {
            User user = UserMapper.toUser(userDTO);
            userRepository.addUser(user);

            Optional<User> newUserOption = userRepository
                    .getUserByEmail(user.getEmail());

            if (newUserOption.isPresent()) {
                User newUser = newUserOption.get();
                UserGetDTO userGetDTO = UserMapper.toDto(newUser);

                return Response.ok(userGetDTO).build();
            }
            throw new IllegalStateException("User could not be retrieved");
        } catch (RuntimeException ex) {
            return Response.status(500).entity(new ErrorMessage(5000, "Server Error: Could not create user")).build();
        }
    }
}
