package dev.ambryn.discordtest.dto;

import java.util.List;

public record UserGetFinestDTO(Long id, String email, String lastname, String firstname, List<String> roles){}
