package dev.ambryn.discordtest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.apache.commons.text.StringEscapeUtils;

public record UserCreateDTO(
        @NotNull(message = "ne peut être vide")
        @Pattern(regexp = "^[a-zA-Z0-9-_]+\\.*[a-zA-Z0-9-_]*@([a-zA-Z0-9]+\\.{1})+([a-zA-Z]){2,3}$", message = "doit être un email valide")
        String email,

        @NotNull(message = "ne peut être vide")
        @Size(min = 8, max = 50, message = "doit contenir entre 8 et 50 caractères")
        @NotBlank
        String password,

        @NotNull(message = "ne peut être vide")
        @NotBlank(message = "doit contenir des characters autre que espace, tabulation etc.")
        @Size(min = 2, max = 50, message = "doit contenir entre 2 et 50 caractères")
        @Pattern(regexp = "^[a-zA-ZàâçéèếïîôöùûüÀÂÇÉÈẾÏÎÔÖÙÛÜ -]+$", message = "ne doit pas contenir de caractères spéciaux")
        String lastname,

        @NotNull(message = "ne peut être vide")
        @NotBlank(message = "doit contenir des characters autre que espace, tabulation etc.")
        @Size(min = 2, max = 50, message = "doit contenir entre 2 et 50 caractères")
        @Pattern(regexp = "^[a-zA-ZàâçéèếïîôöùûüÀÂÇÉÈẾÏÎÔÖÙÛÜ -]+$", message = "ne doit pas contenir de caractères spéciaux")
        String firstname
) {
    public UserCreateDTO(String email, String password, String lastname, String firstname) {
        this.email = StringEscapeUtils.escapeHtml4(email.trim().toLowerCase());
        this.password = password;
        this.lastname = StringEscapeUtils.escapeHtml4(lastname.trim().toUpperCase());
        this.firstname = StringEscapeUtils.escapeHtml4(firstname.trim());
    }
}