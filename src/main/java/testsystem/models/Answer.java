package testsystem.models;

import javax.persistence.*;

@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String formulation;
    private boolean trueness;

    @ManyToOne
    private Question question;
    public Answer() {

    }

    public Answer(Long id, String formulation, Question question, boolean trueness) {
        this.id = id;
        this.formulation = formulation;
        this.question = question;
        this.trueness = trueness;
    }
    public  Answer(Question question, String formulation, boolean trueness){
        this.formulation = formulation;
        this.question = question;

            this.trueness = trueness;


    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getFormulation() {
        return formulation;
    }

    public void setFormulation(String formulation) {
        this.formulation = formulation;
    }

    public void setTrueness(boolean trueness) {
        this.trueness = trueness;
    }

    public Question getQuestion() {return question;}

    public void setQuestion(Question question) {this.question = question;}

    public boolean getTrueness() {
        return trueness;
    }
}
