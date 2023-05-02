package dev.ambryn.discord.dto.user;

import dev.ambryn.discord.dto.channel.ChannelGetDTO;

import java.util.List;

public record UserGetFinestDTO(Long id, String email, String lastname, String firstname, List<String> roles, List<ChannelGetDTO> channels){}
