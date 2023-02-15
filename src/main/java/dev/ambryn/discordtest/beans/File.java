package dev.ambryn.discordtest.beans;

import jakarta.persistence.*;

@Entity
@Table(name = "File")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fil_id", nullable = false)
    private Long id;

    @Column(name = "fil_name")
    private String name;

    @Column(name = "fil_path")
    private String path;

    public Long getId() {
        return id;
    }

}
