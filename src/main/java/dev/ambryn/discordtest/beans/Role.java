package dev.ambryn.discordtest.beans;

import dev.ambryn.discordtest.enums.ERole;
import jakarta.persistence.*;

@Entity
@Table(name = "Role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rol_id", nullable = false)
    private Long id;

    @Column(name = "rol_name", nullable = false, unique = true)
    private ERole name;

    public Role() { }

    public Role(ERole name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public ERole getName() {
        return name;
    }
}
