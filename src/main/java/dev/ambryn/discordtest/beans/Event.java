package dev.ambryn.discordtest.beans;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eve_id", nullable = false)
    private Long id;

    @Column(name = "eve_emitted_at", nullable = false)
    private LocalDateTime emittedAt;

    @OneToOne(targetEntity = Message.class, cascade = CascadeType.DETACH)
    @JoinColumn(name = "eve_message", nullable = false)
    private Message message;

    @OneToOne(targetEntity = Channel.class, cascade = CascadeType.DETACH)
    @JoinColumn(name = "eve_channel", nullable = false)
    private Channel channel;

    public Event() {}

    public Event(Message message, Channel channel) {
        this.message = message;
        this.channel = channel;
        this.emittedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getEmittedAt() {
        return emittedAt;
    }

    public Message getMessage() {
        return message;
    }

    public Channel getChannel() {
        return channel;
    }
}
