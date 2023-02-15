package dev.ambryn.discordtest.controllers;

import dev.ambryn.discordtest.beans.Meeting;
import dev.ambryn.discordtest.beans.User;
import dev.ambryn.discordtest.repositories.UserRepository;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.*;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/users")
public class UserController {

    @Inject
    UserRepository userRepository;

    @GET
    @Produces(value = "application/json")
    public List<User> getUsers() throws HeuristicRollbackException, SystemException, HeuristicMixedException, NotSupportedException, RollbackException {
        return (List<User>) userRepository.getUsers();
    }

    @GET
    @Path("/{id: [0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("id") Long id) {
        return userRepository.getUser(id);
    }
}
