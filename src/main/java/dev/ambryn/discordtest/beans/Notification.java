package dev.ambryn.discordtest.beans;

import jakarta.persistence.*;

@Entity
@Table(name = "Notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "not_id", nullable = false)
    private Long id;

    @Column(name = "not_seen", nullable = false)
    private boolean seen;

    @OneToOne(targetEntity = Subject.class, cascade = CascadeType.DETACH)
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
}
