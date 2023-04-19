package dev.ambryn.discord.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.apache.commons.text.StringEscapeUtils;

public record GroupCreateDTO(
        @NotNull(message = "ne peut être vide")
        @NotBlank
        @Size(min = 1, max = 50, message = "doit contenir entre 1 et 50 caractères")
        String name) {
        public GroupCreateDTO(String name) {
                this.name = name != null ? StringEscapeUtils.escapeHtml4(name.trim().toUpperCase()) : null;
        }
}
