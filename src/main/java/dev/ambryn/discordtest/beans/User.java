package dev.ambryn.discordtest.beans;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.apache.commons.text.StringEscapeUtils;

import java.util.*;

@Entity
@Table(name = "User", uniqueConstraints = @UniqueConstraint(name = "email", columnNames = { "usr_email" }))
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_id", nullable = false)
    private Long id;

    @Column(name = "usr_email", nullable = false)
    @NotNull(message = "ne peut être vide")
    @Pattern(regexp = "^[a-zA-Z0-9-_]+\\.*[a-zA-Z0-9-_]*@([a-zA-Z0-9]+\\.{1})+([a-zA-Z]){2,3}$", message = "doit être un email valide")
    private String email;

    @Column(name = "usr_password", nullable = false)
    @NotNull(message = "ne peut être vide")
    @Size(min = 8, max = 200)
    @NotBlank
    private String password;

    @Column(name = "usr_lastname", nullable = false)
    @NotNull(message = "ne peut être vide")
    @NotBlank
    @Size(min = 2, max = 50)
    @Pattern(regexp = "^[a-zA-ZàâçéèếïîôöùûüÀÂÇÉÈẾÏÎÔÖÙÛÜ -]+$", message = "ne doit pas contenir de caractères spéciaux")
    private String lastname;

    @Column(name = "usr_firstname", nullable = false)
    @NotNull(message = "ne peut être vide")
    @NotBlank(message = "doit contenir des characters autre que espace, tabulation etc.")
    @Size(min = 2, max = 50)
    @Pattern(regexp = "^[a-zA-ZàâçéèếïîôöùûüÀÂÇÉÈẾÏÎÔÖÙÛÜ -]+$", message = "ne doit pas contenir de caractères spéciaux")
    private String firstname;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "has_role",
            joinColumns = @JoinColumn(name = "usr_id", referencedColumnName = "usr_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id", referencedColumnName = "rol_id")
    )
    private List<Role> roles;

    @OneToMany(orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinTable(name = "is_notified_of",
            joinColumns = @JoinColumn(name = "usr_id", referencedColumnName = "usr_id"),
            inverseJoinColumns = @JoinColumn(name = "not_id", referencedColumnName = "not_id")
    )
    private ArrayDeque<Notification> queue;

    public User() {
        this.roles = new ArrayList<>();
        this.queue = new ArrayDeque<>();
    }

    public User(String email, String password, String lastname, String firstname) {
        this.email = email;
        this.password = password;
        this.lastname = lastname;
        this.firstname = firstname;
        this.roles = new ArrayList<>();
        this.queue = new ArrayDeque<>();
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
    }

    public void enqueuEvent(Notification notification) {
        this.queue.add(notification);
    }

    public void dequeueEvent() {
        this.queue.poll();
    }

    public Long getId() {
        return id;
    }

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

    public List<Role> getRoles() {
        return roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", lastname='" + lastname + '\'' +
                ", firstname='" + firstname + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id)
                && Objects.equals(email, user.email)
                && Objects.equals(password, user.password)
                && Objects.equals(lastname, user.lastname)
                && Objects.equals(firstname, user.firstname)
                && Objects.equals(roles, user.roles)
                && Objects.equals(queue, user.queue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, lastname, firstname, roles, queue);
    }
}