package testsystem.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String formulation;
    private int num;

    @ManyToOne
    private Subject subject;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers = new ArrayList<Answer>();

    public Question(){

    }

    public Question(Subject subject, String formulation, int num) {
        this.formulation = formulation;
        this.subject = subject;
        this.num = num;
    }

    public Question(Long id, List<Answer> answers, String formulation, int num, Subject subject) {
        this.formulation = formulation;
        this.subject = subject;
        this.num = num;
        this.id = id;
        this.answers = answers;
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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Subject getSubject() {return subject;}

    public void setSubject(Subject subject) {this.subject = subject;}

    public List<Answer> getAnswers() {return answers;}

    public void setAnswers(List<Answer> answers) {this.answers = answers;}
}
