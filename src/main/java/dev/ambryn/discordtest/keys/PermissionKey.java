package dev.ambryn.discordtest.keys;

import java.io.Serializable;
import java.util.Objects;

public class PermissionKey implements Serializable {
    private Long user;
    private Long role;
    private Long channel;

    public PermissionKey() {}

    public PermissionKey(Long user, Long role, Long channel) {
        this.user = user;
        this.role = role;
        this.channel = channel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PermissionKey that = (PermissionKey) o;
        return user.equals(that.user) && role.equals(that.role) && channel.equals(that.channel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, role, channel);
    }

    public Long getUser() {
        return user;
    }

    public Long getRole() {
        return role;
    }

    public Long getChannel() {
        return channel;
    }
}
