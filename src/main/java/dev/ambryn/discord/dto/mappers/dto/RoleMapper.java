package dev.ambryn.discord.dto.mappers.dto;

import dev.ambryn.discord.beans.Role;
import dev.ambryn.discord.dto.RoleGetDTO;
import dev.ambryn.discord.enums.ERole;

public class RoleMapper {

    public static RoleGetDTO toDTO(Role role) {
        return new RoleGetDTO(role.getName().name());
    }

    public static Role toRole(RoleGetDTO dto) {
        return new Role(ERole.valueOf(dto.name()));
    }
}
