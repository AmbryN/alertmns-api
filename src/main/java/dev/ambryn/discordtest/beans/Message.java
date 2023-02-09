package dev.ambryn.discordtest.beans;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "msg_id", nullable = false)
    private Long id;

    @OneToOne(targetEntity = User.class, cascade = CascadeType.DETACH)
    @JoinColumn(name = "msg_sender",nullable = false)
    private User sender;

    @Column(name = "msg_content", nullable = false)
    private String content;

    @Column(name = "msg_file_path", nullable = true)
    private String filePath;

    @Column(name = "msg_sent_at", nullable = false)
    private LocalDateTime sentAt;

    public Message() {
        this.sentAt = LocalDateTime.now();
    }

    public Message(User sender, String content) {
        this.sender = sender;
        this.content = content;
        this.filePath = null;
        this.sentAt = LocalDateTime.now();
    }

    public Message(User sender, String content, String filePath) {
        this.sender = sender;
        this.content = content;
        this.filePath = filePath;
        this.sentAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public User getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public String getFilePath() {
        return filePath;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }
}
