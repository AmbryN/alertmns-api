package dev.ambryn.discordtest.beans;

import jakarta.persistence.*;

import java.util.Objects;

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

    @Override
    public String toString() {
        return "Message{" +
                "sender=" + sender +
                ", content='" + content + '\'' +
                ", file=" + file +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(sender, message.sender)
                && Objects.equals(content, message.content)
                && Objects.equals(file, message.file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sender, content, file);
    }
}