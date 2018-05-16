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
}
