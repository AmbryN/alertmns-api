package dev.ambryn.discordtest.dto;

public class UserGetDTO {
    private final Long id;
    private final String email;

    private final String lastname;

    private final String firstname;

    public UserGetDTO(Long id, String email, String lastname, String firstname) {
        this.id = id;
        this.email = email;
        this.lastname = lastname;
        this.firstname = firstname;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }
}
