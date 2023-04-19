package dev.ambryn.discord.dto;

import dev.ambryn.discord.enums.ERole;

public record JwtPayload(Long id, String email, ERole[] roles) {
}
