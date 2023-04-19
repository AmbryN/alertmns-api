package dev.ambryn.discord.controllers;

import dev.ambryn.discord.beans.Group;
import dev.ambryn.discord.beans.User;
import dev.ambryn.discord.dto.AddDTO;
import dev.ambryn.discord.dto.group.GroupCreateDTO;
import dev.ambryn.discord.dto.group.GroupGetDTO;
import dev.ambryn.discord.dto.mappers.dto.GroupMapper;
import dev.ambryn.discord.dto.mappers.dto.UserMapper;
import dev.ambryn.discord.enums.ERole;
import dev.ambryn.discord.errors.DataAccessException;
import dev.ambryn.discord.filters.Authorize;
import dev.ambryn.discord.repositories.GroupRepository;
import dev.ambryn.discord.repositories.UserRepository;
import dev.ambryn.discord.responses.NotFound;
import dev.ambryn.discord.responses.Ok;
import dev.ambryn.discord.responses.ServerError;
import dev.ambryn.discord.validators.BeanValidator;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Path("/groups")
public class GroupController {

    private final Logger logger = LoggerFactory.getLogger("GroupController");

    @Inject
    GroupRepository groupRepository;
    @Inject
    UserRepository userRepository;

    @GET
    @Produces(value = "application/json")
    public Response getGroups() {
        logger.debug("Getting all groups");
        List<GroupGetDTO> groups = groupRepository.getGroups()
                .stream().map(GroupMapper::toDTO)
                .toList();
        return Ok.build(groups);
    }

    @GET
    @Path("/{id:[0-9+]}")
    @Produces(value = "application/json")
    public Response getGroup(@PathParam("id") Long id) {
        return groupRepository.getGroup(id)
                .map(GroupMapper::toFinestDTO)
                .map(Ok::build)
                .orElseGet(() -> NotFound.build("Could not find group with id=" + id));
    }

    @GET
    @Path("/{id:[0-9+]}/members")
    @Produces(value = "application/json")
    public Response getMembers(@PathParam("id") Long id) {
        return groupRepository.getGroup(id)
                .map(Group::getMembers)
                .map(users -> users.stream().map(UserMapper::toDto).toList())
                .map(Ok::build)
                .orElseGet(() -> NotFound.build("Could not find group with id=" + id));
    }

    @POST
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

    @POST
    @Path("/{id:[0-9]+}/members")
    @Consumes(MediaType.APPLICATION_JSON)
    @Authorize(level = ERole.ADMIN)
    public Response addMember(@PathParam("id") Long id, AddDTO userToAdd) {
        BeanValidator.validate(userToAdd);

        Response response = null;
        try {
            Optional<Group> oGroup = groupRepository.getGroup(id);

            if (oGroup.isPresent()) {
                Group group = oGroup.get();
                Optional<User> oUser = userRepository.getUser(userToAdd.id());

                if (oUser.isPresent()) {
                    User user = oUser.get();
                    group.addMember(user);
                    groupRepository.saveGroup(group);

                    response = Ok.build();
                } else {
                    response = NotFound.build("Could not find user with id=" + userToAdd.id());
                }
            } else {
                response = NotFound.build("Could not find group with id=" + id);
            }
        } catch (DataAccessException dae) {
            return ServerError.build(dae.getMessage());
        }
        return response;
    }
}
