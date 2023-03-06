package dev.ambryn.discordtest.beans;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "Notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "not_id", nullable = false)
    private Long id;

    @Column(name = "not_seen", nullable = false)
    private boolean seen;

    @OneToOne(targetEntity = Subject.class)
    @JoinColumn(name = "not_subject", nullable = false)
    private Subject subject;

    public Notification() {}

    public Notification(Subject sub) {
       this.subject = sub;
    }

    public Long getId() {
        return id;
    }

    public boolean isSeen() {
        return seen;
    }

    public Subject getSubject() {
        return subject;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", seen=" + seen +
                ", subject=" + subject +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return seen == that.seen && Objects.equals(id, that.id) && Objects.equals(subject, that.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, seen, subject);
    }
}
