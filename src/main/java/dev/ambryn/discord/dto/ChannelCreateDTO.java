package dev.ambryn.discord.dto;

import jakarta.validation.constraints.*;
import org.apache.commons.text.StringEscapeUtils;

public record ChannelCreateDTO(
        @NotNull(message = "ne peut être vide")
        @NotBlank
        @Size(min = 1, max = 50, message = "doit contenir entre 1 et 50 caractères")
        String name,

        @NotNull(message = "ne peut être vide")
        @NotBlank
        @Pattern(regexp = "^((PUBLIC)|(PRIVATE))$", message = "doit être PUBLIC ou PRIVATE")
        String visibility) {

        public ChannelCreateDTO(String name, String visibility) {
                this.name = name != null ? StringEscapeUtils.escapeHtml4(name.trim().toUpperCase()) : null;
                this.visibility = visibility != null ? StringEscapeUtils.escapeHtml4(visibility.trim().toUpperCase()) : null;
        }
}
