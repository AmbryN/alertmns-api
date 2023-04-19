package dev.ambryn.discord.dto.mappers.dto;

import dev.ambryn.discord.beans.Role;
import dev.ambryn.discord.dto.RoleDTO;
import dev.ambryn.discord.enums.ERole;

public class RoleMapper {

    public static RoleDTO toDTO(Role role) {
        return new RoleDTO(role.getId(), role.getName());
    }

    public static Role toRole(RoleDTO dto) {
        return new Role(ERole.valueOf(dto.name().toString()));
    }
}
