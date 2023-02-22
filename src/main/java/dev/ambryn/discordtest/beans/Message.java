package dev.ambryn.discordtest.beans;

import jakarta.persistence.*;

@Entity
@Table(name = "Message")
public class Message extends Subject {

    @OneToOne
    @JoinColumn(name = "msg_sender", nullable = false)
    private User sender;

    @Column(name = "msg_content", nullable = false)
    private String content;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "msg_file", nullable = true)
    private File file;

    public Message() {
        super();
    }

    public Message(User sender, String content) {
        this(sender, content, null);
    }

    public Message(User sender, String content, File file) {
        super();
        this.sender = sender;
        this.content = content;
        this.file = file;
    }

    public User getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public File getFile() {
        return file;
    }
}