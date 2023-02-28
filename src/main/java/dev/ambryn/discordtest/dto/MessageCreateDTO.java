package dev.ambryn.discordtest.dto;

import jakarta.validation.constraints.*;
import org.apache.commons.text.StringEscapeUtils;

public record MessageCreateDTO(
        @NotNull(message = "ne peut être vide")
        @Digits(message = "doit être un entier positif", integer = Integer.SIZE, fraction = 0)
        @Positive
        Long userId,

        @NotNull(message = "ne peut être vide")
        @NotBlank
        @Size(min = 1, max = 2000, message = "doit contenir entre 1 et 2000 caractères")
        String content) {

        public MessageCreateDTO(Long userId, String content) {
                this.userId = userId;
                this.content = StringEscapeUtils.escapeHtml4(content);
        }
}
