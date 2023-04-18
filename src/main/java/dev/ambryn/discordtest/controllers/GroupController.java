package dev.ambryn.discordtest.controllers;

import dev.ambryn.discordtest.beans.Group;
import dev.ambryn.discordtest.dto.GroupCreateDTO;
import dev.ambryn.discordtest.dto.GroupGetDTO;
import dev.ambryn.discordtest.dto.mappers.dto.GroupMapper;
import dev.ambryn.discordtest.errors.DataAccessException;
import dev.ambryn.discordtest.repositories.GroupRepository;
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


public class GroupController {

    private final Logger logger = LoggerFactory.getLogger("GroupController");

    @Inject
    GroupRepository groupRepository;

    @GET
    @Path("/groups")
    @Produces(value = "application/json")
    public Response getGroups() {
        logger.debug("Getting all groups");
        List<GroupGetDTO> groups = groupRepository.getGroups()
                .stream().map(GroupMapper::toDTO)
                .toList();
        return Ok.build(groups);
    }

    @GET
    @Path("/groups/{id:[0-9+]}")
    @Produces(value = "application/json")
    public Response getGroup(@PathParam("id") Long id) {
       return groupRepository.getGroup(id)
               .map(GroupMapper::toFinestDTO)
               .map(Ok::build)
               .orElseGet(() -> NotFound.build("Could not find group with id=" + id));
    }

    @POST
    @Path("/groups")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createGroup(GroupCreateDTO newGroup) {
        BeanValidator.validate(newGroup);

        Group group = GroupMapper.toGroup(newGroup);

        Response response = null;
        try {
            groupRepository.saveGroup(group);
            response = Ok.build(group);
        } catch (DataAccessException dae) {
            response = ServerError.build(dae.getMessage());
        }
        return response;
    }
}
