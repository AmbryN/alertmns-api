package dev.ambryn.discordtest.beans;

import jakarta.persistence.*;

@Entity
@Table(name = "Role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rol_id", nullable = false)
    private Long id;

    @Column(name = "rol_name", nullable = false, unique = true)
    private String name;

    public Role() { }

    public Role(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
