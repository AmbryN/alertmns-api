package dev.ambryn.discordtest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.apache.commons.text.StringEscapeUtils;

public class UserPostDTO {

    @NotNull(message = "ne peut être vide")
    @Pattern(regexp = "^[a-zA-Z0-9-_]+\\.*[a-zA-Z0-9-_]*@([a-zA-Z0-9]+\\.{1})+([a-zA-Z]){2,3}$", message = "doit être un email valide")
    private String email;

    @NotNull(message = "ne peut être vide")
    @Size(min = 8, max = 50)
    @NotBlank
    private String password;

    @NotNull(message = "ne peut être vide")
    @NotBlank
    @Size(min = 2, max = 50)
    @Pattern(regexp = "^[a-zA-ZàâçéèếïîôöùûüÀÂÇÉÈẾÏÎÔÖÙÛÜ -]+$", message = "ne doit pas contenir de caractères spéciaux")
    private String lastname;

    @NotNull(message = "ne peut être vide")
    @NotBlank(message = "doit contenir des characters autre que espace, tabulation etc.")
    @Size(min = 2, max = 50)
    @Pattern(regexp = "^[a-zA-ZàâçéèếïîôöùûüÀÂÇÉÈẾÏÎÔÖÙÛÜ -]+$", message = "ne doit pas contenir de caractères spéciaux")
    private String firstname;

    public UserPostDTO() {}

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setEmail(String email) {
        this.email = email.trim().toLowerCase();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLastname(String lastname) {
        this.lastname = StringEscapeUtils.escapeHtml4(lastname.trim().toUpperCase());
    }

    public void setFirstname(String firstname) {
        this.firstname = StringEscapeUtils.escapeHtml4(firstname.trim());
    }
}