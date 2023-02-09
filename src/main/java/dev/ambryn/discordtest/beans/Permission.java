package dev.ambryn.discordtest.beans;

import dev.ambryn.discordtest.keys.PermissionKey;
import jakarta.persistence.*;

@IdClass(PermissionKey.class)
@Entity
@Table(name = "Permission")
public class Permission {
    @Id
    @ManyToOne(targetEntity = User.class, cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "per_user", nullable = false)
    private User user;

    @Id
    @ManyToOne(targetEntity = ChannelRole.class, cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "per_role", nullable = false)
    private ChannelRole role;

    @Id
    @ManyToOne(targetEntity = Channel.class, cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "per_channel", nullable = false)
    private Channel channel;

    public Permission() {

    }

    public Permission(User user, ChannelRole role, Channel channel) {
        this.user = user;
        this.role = role;
        this.channel = channel;
    }

    public User getUser() {
        return user;
    }

    public ChannelRole getRole() {
        return role;
    }

    public Channel getChannel() {
        return channel;
    }
}
