package dev.ambryn.discordtest;

import dev.ambryn.discordtest.beans.User;
import dev.ambryn.discordtest.repositories.UserRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import java.util.List;

@Path("/hello-world")
public class HelloResource {
    @Inject
    UserRepository repo;

    @GET
    @Produces("text/plain")
    public String hello() {
        Iterable<User> list = repo.getUsers();
        if (list.iterator().hasNext()) return "Hello, " + list.iterator().next().getFirstname();
        return "Hello, World!";
    }
}