package dev.ambryn.discordtest.beans;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Private_Message")
public class PrivateMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pms_id", nullable = false)
    private Long id;

    @OneToOne(targetEntity = User.class, cascade = CascadeType.DETACH)
    @JoinColumn(name = "pms_sender", nullable = false)
    private User sender;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "pms_receiver", nullable = false)
    private User receiver;

    @Column(name = "pms_content", nullable = false)
    private String content;

    @Column(name = "pms_sent_at", nullable = false)
    private LocalDateTime sentAt;

    @Column(name = "pms_seen", nullable = false)
    private boolean seen;

    public PrivateMessage() {
        this.sentAt = LocalDateTime.now();
        this.seen = false;
    }

    public PrivateMessage(User sender, User receiver, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.sentAt = LocalDateTime.now();
        this.seen = false;
    }

    public Long getId() {
        return id;
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public boolean isSeen() {
        return seen;
    }
}
