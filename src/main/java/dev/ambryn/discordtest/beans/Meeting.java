package dev.ambryn.discordtest.beans;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@ToString
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
