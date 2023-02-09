package dev.ambryn.discordtest.beans;

import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
@Table(name = "Channel")
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cha_id", nullable = false)
    private Long id;

    @Column(name = "cha_name", nullable = false)
    private String name;

    @ManyToMany(targetEntity = User.class, cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinTable(name = "is_allowed_in",
            joinColumns = @JoinColumn(name = "cha_id", referencedColumnName = "cha_id"),
            inverseJoinColumns = @JoinColumn(name = "usr_id", referencedColumnName = "usr_id")
    )
    private ArrayList<User> members;

    @ManyToMany(targetEntity = User.class, cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinTable(name = "has_subscribed_to",
            joinColumns = @JoinColumn(name = "cha_id", referencedColumnName = "cha_id"),
            inverseJoinColumns = @JoinColumn(name = "usr_id", referencedColumnName = "usr_id")
    )
    private ArrayList<User> subscribers;

    @OneToMany(targetEntity = Message.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "contains",
            joinColumns = @JoinColumn(name = "cha_id", referencedColumnName = "cha_id"),
            inverseJoinColumns = @JoinColumn(name = "msg_id", referencedColumnName = "msg_id")
    )
    private ArrayList<Message> messages;

    public Channel() {}

    public Channel(String name) {
        this.name = name;
    }

    public void addMember(User member) {
        this.members.add(member);
    }

    public void removeMember(User member) {
        this.members.remove(member);
    }

    public void addSubscriber(User sub) {
        this.subscribers.add(sub);
    }

    public void removeSubscriber(User sub) {
        this.subscribers.remove(sub);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<User> getSubscribers() {
        return subscribers;
    }

    public ArrayList<User> getMembers() {
        return members;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }
}
