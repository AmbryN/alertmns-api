package dev.ambryn.discordtest.beans;

import dev.ambryn.discordtest.enums.EVisibility;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public String toString() {
        return "Channel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", visibility=" + visibility +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Channel channel = (Channel) o;
        return Objects.equals(id, channel.id)
                && Objects.equals(name, channel.name)
                && visibility == channel.visibility
                && Objects.equals(members, channel.members)
                && Objects.equals(subscribers, channel.subscribers)
                && Objects.equals(messages, channel.messages)
                && Objects.equals(meetings, channel.meetings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, visibility, members, subscribers, messages, meetings);
    }
}
