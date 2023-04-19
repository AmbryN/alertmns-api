package dev.ambryn.discord.dto;

import jakarta.validation.constraints.*;
import org.apache.commons.text.StringEscapeUtils;

public record AddDTO(
        @Positive
        Long id
) {}