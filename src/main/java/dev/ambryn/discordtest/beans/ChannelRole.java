package dev.ambryn.discordtest.beans;

import jakarta.persistence.*;

@Entity
@Table(name = "Channel_Role")
public class ChannelRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chr_id", nullable = false)
    private Long id;

    @Column(name = "chr_name", nullable = false, unique = true)
    private String name;

    public ChannelRole() {}

    public ChannelRole(String name, Channel channel) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
