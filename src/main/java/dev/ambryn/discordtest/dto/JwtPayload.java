package dev.ambryn.discordtest.dto;

import dev.ambryn.discordtest.enums.ERole;

public record JwtPayload(Long id, String email, ERole[] roles) {
}
