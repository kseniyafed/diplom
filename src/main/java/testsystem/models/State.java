package testsystem.models;

import javax.persistence.*;

@Entity
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private int position;

    @ManyToOne
    private User user;

    @ManyToOne
    private Subject subject;

    public State() {
    }


    public State(int position, User user, Subject subject) {
        this.position = position;
        this.subject = subject;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
