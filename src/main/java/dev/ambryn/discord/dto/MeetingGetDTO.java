package dev.ambryn.discord.dto;

import java.sql.Timestamp;

public record MeetingGetDTO(
        Long id,
        UserGetDTO organizer,
        Timestamp datetime,
        int duration) {}
