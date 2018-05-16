package testsystem.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long num;


    private String name;

    @OneToMany(mappedBy = "subject")
    @OrderBy("num ASC")
    private List<Result> results = new ArrayList<Result>();


    @OneToMany(mappedBy = "subject")
    @OrderBy("num ASC")
    private List<Question> questions = new ArrayList<Question>();

    public Subject() {

    }

    public Subject(Long id, Long num, String name) {
        this.id = id;
        this.num = num;
        this.name = name;
    }

    public Subject(String name, Long num) {
        this.name = name;
        this.num = num;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
