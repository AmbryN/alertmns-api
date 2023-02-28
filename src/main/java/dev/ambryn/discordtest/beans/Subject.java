package dev.ambryn.discordtest.beans;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Subject")
public abstract class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_id", nullable = false)
    protected Long id;

    @Column(name = "sub_sent_at", nullable = false)
    protected LocalDateTime sentAt;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "sub_channel", nullable = false)
    protected Channel channel;

    public Subject() {
        this.sentAt = LocalDateTime.now();
    }

    public Subject(Channel channel) {
        this.sentAt = LocalDateTime.now();
        this.channel = channel;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
