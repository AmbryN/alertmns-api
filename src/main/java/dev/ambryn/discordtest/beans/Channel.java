package dev.ambryn.discordtest.beans;

import dev.ambryn.discordtest.enums.EVisibility;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Channel")
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cha_id", nullable = false)
    private Long id;

    @Column(name = "cha_name", nullable = false)
    private String name;

    @Column(name = "cha_visibility", nullable = false)
    private EVisibility visibility;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "is_allowed_in",
            joinColumns = @JoinColumn(name = "cha_id", referencedColumnName = "cha_id"),
            inverseJoinColumns = @JoinColumn(name = "usr_id", referencedColumnName = "usr_id")
    )
    private ArrayList<User> members;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "has_subscribed_to",
            joinColumns = @JoinColumn(name = "cha_id", referencedColumnName = "cha_id"),
            inverseJoinColumns = @JoinColumn(name = "usr_id", referencedColumnName = "usr_id")
    )
    private ArrayList<User> subscribers;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "channel")
    private List<Message> messages;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "channel")
    private List<Meeting> meetings;

    public Channel() {}

    public Channel(String name, EVisibility visibility) {
        this.name = name;
        this.visibility = visibility;
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

    public void addMeeting(Meeting meeting) {
        this.meetings.add(meeting);
    }

    public void removeMeeting(Meeting meeting) {
        this.meetings.remove(meeting);
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public EVisibility getVisibility() {
        return visibility;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVisibility(EVisibility visibility) {
        this.visibility = visibility;
    }

    public List<User> getSubscribers() {
        return subscribers;
    }

    public List<User> getMembers() {
        return members;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public List<Meeting> getMeetings() {
        return meetings;
    }

    public void notifySubscribers()  {
        // TODO
    }
}
