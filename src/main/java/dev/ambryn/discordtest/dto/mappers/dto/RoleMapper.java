package dev.ambryn.discordtest.dto.mappers.dto;

import dev.ambryn.discordtest.beans.Channel;
import dev.ambryn.discordtest.beans.Role;
import dev.ambryn.discordtest.dto.ChannelCreateDTO;
import dev.ambryn.discordtest.dto.ChannelGetDTO;
import dev.ambryn.discordtest.dto.RoleGetDTO;
import dev.ambryn.discordtest.enums.EError;
import dev.ambryn.discordtest.enums.ERole;
import dev.ambryn.discordtest.enums.EVisibility;

public class RoleMapper {

    public static RoleGetDTO toDTO(Role role) {
        return new RoleGetDTO(role.getName().name());
    }

    public static Role toRole(RoleGetDTO dto) {
        return new Role(ERole.valueOf(dto.name()));
    }
}
