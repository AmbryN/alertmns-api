package dev.ambryn.discordtest.beans;

import dev.ambryn.discordtest.enums.EVisibility;
import jakarta.persistence.*;

@Entity
@Table(name = "Visibility")
public class Visibility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vis_id")
    private Long id;

    @Column(name = "vis_name")
    private EVisibility name;

    public Long getId() {
        return id;
    }

    public EVisibility getName() {
        return name;
    }
}
