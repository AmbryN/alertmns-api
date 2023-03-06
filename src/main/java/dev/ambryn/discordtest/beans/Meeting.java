package dev.ambryn.discordtest.beans;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

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

    @Override
    public String toString() {
        return "Meeting{" +
                "name='" + name + '\'' +
                ", dateTime=" + dateTime +
                ", duration=" + duration +
                ", organizer=" + organizer +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meeting meeting = (Meeting) o;
        return duration == meeting.duration
                && Objects.equals(name, meeting.name)
                && Objects.equals(dateTime, meeting.dateTime)
                && Objects.equals(organizer, meeting.organizer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, dateTime, duration, organizer);
    }
}
