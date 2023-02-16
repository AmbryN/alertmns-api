package dev.ambryn.discordtest.controllers;

import dev.ambryn.discordtest.beans.User;
import dev.ambryn.discordtest.repositories.UserRepository;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/users")
public class UserController {

    @Inject
    UserRepository userRepository;

    @GET
    @Produces(value = "application/json")
    public List<User> getUsers() {
        return (List<User>) userRepository.getUsers();
    }

    @GET
    @Path("/{id: [0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("id") Long id) {
        User user = new User("test", "test", "test", "test");
        return user;
//        return userRepository.getUser(id);
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response postUser(@Valid User user) {
        return Response.ok(user).build();
    }
}
