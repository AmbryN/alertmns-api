package dev.ambryn.discordtest.beans;

import jakarta.persistence.*;

@Entity
@Table(name = "Message")
public class Message extends Subject {

    @ManyToOne
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

    public Message(Channel channel, User sender, String content) {
        this(channel, sender, content, null);
    }

    public Message(Channel channel, User sender, String content, File file) {
        super(channel);
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

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setFile(File file) {
        this.file = file;
    }
}