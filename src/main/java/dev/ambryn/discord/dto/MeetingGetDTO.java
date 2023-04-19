package dev.ambryn.discord.dto;

import dev.ambryn.discord.dto.user.UserGetDTO;

import java.sql.Timestamp;

public record MeetingGetDTO(
        Long id,
        UserGetDTO organizer,
        Timestamp datetime,
        int duration) {}
