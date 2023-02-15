package dev.ambryn.discordtest.beans;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.*;

@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "usr_email")
    private String email;

    @NotNull
    @Column(name = "usr_password")
    private String password;

    @NotNull
    @Column(name = "usr_lastname")
    private String lastname;

    @NotNull
    @Column(name = "usr_firstname")
    private String firstname;

    @ManyToMany(targetEntity = Role.class, cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinTable(name = "has_role",
            joinColumns = @JoinColumn(name = "usr_id", referencedColumnName = "usr_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id", referencedColumnName = "rol_id")
    )
    private List<Role> roles;

    @ManyToMany(targetEntity = Notification.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "is_notified_of",
            joinColumns = @JoinColumn(name = "usr_id", referencedColumnName = "usr_id"),
            inverseJoinColumns = @JoinColumn(name = "not_id", referencedColumnName = "not_id")
    )
    private Deque<Notification> queue;

    public User() {
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

    public List<Role> getRoles() {
        return roles;
    }
}