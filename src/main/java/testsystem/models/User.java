package testsystem.models;


import org.springframework.web.bind.annotation.ModelAttribute;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;

    private String password;

    @OrderColumn
    private String name;

    private String surname;

    @ManyToOne
    private UserGroup userGroup;

    @OneToMany(mappedBy = "user")
    private List<Result> results = new ArrayList<Result>();

    public User() {
    }

    public User(Long id, String name, String login, String password) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
    }

    public User(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public User(UserGroup userGroup, String login, String password, String name) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.userGroup = userGroup;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ModelAttribute("login")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}
