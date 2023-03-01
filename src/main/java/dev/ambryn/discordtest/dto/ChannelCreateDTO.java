package dev.ambryn.discordtest.dto;

import jakarta.validation.constraints.*;
import org.apache.commons.text.StringEscapeUtils;

public record ChannelCreateDTO(
        @NotNull(message = "ne peut être vide")
        @NotBlank
        @Size(min = 1, max = 50, message = "doit contenir entre 1 et 50 caractères")
        String name,

        @NotNull(message = "ne peut être vide")
        @NotBlank
        @Pattern(regexp = "^((PUBLIC)|(PRIVATE)){1}$", message = "doit être PUBLIC ou PRIVATE")
        String visibility) {

        public ChannelCreateDTO(String name, String visibility) {
                this.name = StringEscapeUtils.escapeHtml4(name.trim().toUpperCase());
                this.visibility = StringEscapeUtils.escapeHtml4(visibility.trim().toUpperCase());
        }
}
