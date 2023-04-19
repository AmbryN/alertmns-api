package dev.ambryn.discord.controllers;

import dev.ambryn.discord.dto.RoleDTO;
import dev.ambryn.discord.dto.group.GroupGetDTO;
import dev.ambryn.discord.dto.mappers.dto.GroupMapper;
import dev.ambryn.discord.dto.mappers.dto.RoleMapper;
import dev.ambryn.discord.enums.ERole;
import dev.ambryn.discord.filters.Authorize;
import dev.ambryn.discord.repositories.RoleRepository;
import dev.ambryn.discord.responses.Ok;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Path("/roles")
@Authorize(level = ERole.ADMIN)
public class RoleController {

    private final Logger logger = LoggerFactory.getLogger("RoleController");

    @Inject
    RoleRepository roleRepository;

    @GET
    @Produces(value = "application/json")
    public Response getRoles() {
        logger.debug("Getting all roles");
        List<RoleDTO> roles = roleRepository.getRoles()
                .stream().map(RoleMapper::toDTO)
                .toList();
        return Ok.build(roles);
    }
}
