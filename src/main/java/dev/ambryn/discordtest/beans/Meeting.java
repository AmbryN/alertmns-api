package dev.ambryn.discordtest.beans;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Meeting")
public class Meeting extends Subject {

    @Column(name = "mee_name")
    private String name;

    @Column(name = "mee_datetime")
    private LocalDateTime dateTime;

    @Column(name = "mee_duration")
    private int duration;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "mee_user", nullable = false)
    private User organizer;

//    @ManyToOne(cascade = CascadeType.DETACH)
//    @JoinColumn(name = "mee_channel", nullable = false)
//    private Channel channel;

    public Meeting() {
        this(null);
    }

    public Meeting(String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public int getDuration() {
        return duration;
    }

    public User getOrganizer() {
        return organizer;
    }
}
