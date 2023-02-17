package dev.ambryn.discordtest.beans;

import jakarta.persistence.*;
import org.apache.commons.text.StringEscapeUtils;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "User_Group")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gro_id", nullable = false)
    private Long id;

    @Column(name = "gro_name", nullable = false, unique = true)
    private String name;

    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinTable(name = "is_member_of",
            joinColumns = @JoinColumn(name = "gro_id", referencedColumnName = "gro_id"),
            inverseJoinColumns = @JoinColumn(name = "usr_id", referencedColumnName = "usr_id")
    )
    private List<User> members;

    protected Group() {
        this(null);
    }

    public Group(String name) {
        this.name = name;
        this.members = new ArrayList<>();
    }

    public void addMember(User user) {
        this.members.add(user);
    }

    public void removeMember(User user) {
        this.members.remove(user);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = StringEscapeUtils.escapeHtml4(name).trim();
    }

    public List<User> getMembers() {
        return members;
    }
}
