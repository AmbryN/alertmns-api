package dev.ambryn.discord.dto.mappers.dto;

import dev.ambryn.discord.beans.Group;
import dev.ambryn.discord.dto.GroupCreateDTO;
import dev.ambryn.discord.dto.GroupGetDTO;
import dev.ambryn.discord.dto.GroupGetFinestDTO;

public class GroupMapper {

    public static GroupGetDTO toDTO(Group group) {
        return new GroupGetDTO(group.getId(), group.getName());
    }

    public static GroupGetFinestDTO toFinestDTO(Group group) {
        return new GroupGetFinestDTO(
                group.getId(),
                group.getName(),
                group.getMembers().stream().map(UserMapper::toDto).toList());
    }

    public static Group toGroup(GroupCreateDTO dto) {
        Group group = new Group();
        group.setId(group.getId());
        group.setName(dto.name());
        return group;
    }
}
