package dev.ambryn.discordtest.controllers;

import dev.ambryn.discordtest.beans.User;
import dev.ambryn.discordtest.dto.UserGetDTO;
import dev.ambryn.discordtest.dto.UserPostDTO;
import dev.ambryn.discordtest.errors.ErrorMessage;
import dev.ambryn.discordtest.mappers.dto.UserMapper;
import dev.ambryn.discordtest.repositories.UserRepository;
import dev.ambryn.discordtest.validators.BeanValidator;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import jakarta.validation.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/users")
public class UserController {

    @Inject
    UserRepository userRepository;

    @Inject
    UserMapper userMapper;

    @GET
    @Produces(value = "application/json")
    public List<UserGetDTO> getUsers() throws ClassNotFoundException {
        List<UserGetDTO> usersDTO = new ArrayList<>();
        for (User user : userRepository.getUsers()) {
            usersDTO.add(userMapper.toDto(user));
        }
        return usersDTO;
    }

    @GET
    @Path("/{id: [0-9]+}")
    @Produces(APPLICATION_JSON)
    public Response getUser(@PathParam("id") Long id) {
        try {
            User user = userRepository.getUser(id);

            UserGetDTO userGetDTO = userMapper.toDto(user);
            return Response.ok(userGetDTO).build();
        } catch (NoResultException ex) {
            return Response.status(404).entity(new ErrorMessage(1000, ex.getMessage())).build();
        }
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response postUser(UserPostDTO userDTO) {
        BeanValidator.validate(userDTO);

        User user = userMapper.toUser(userDTO);

        try {
            userRepository.addUser(user);

            UserGetDTO userGetDTO = userMapper.toDto(user);
            return Response.ok(userGetDTO).build();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return Response.status(400).build();
        }
    }
}
