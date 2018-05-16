package testsystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import testsystem.models.Answer;
import testsystem.models.Question;
import testsystem.services.AnswerService;
import testsystem.services.QuestionService;

import java.util.ArrayList;



@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
public class Test {
    private ArrayList<Answer> answers;

    private ArrayList<Question> studentQuestions;

    private int number = 0;

    @Autowired
    private QuestionService questionService;

    public Test() {
        answers = new ArrayList<Answer>();
        studentQuestions = new ArrayList<Question>();
    }

    public void setAnswer(Answer answer) {
        answers.add(answer);
    }

    public void increaseNumber() {
        this.number++;
    }

    public void reduceNumber() {
        this.number=0;
    }

    public int getNumber() {
        return number;
    }

    public int getMark() {

        int correctAnswers = 0;
        int corrects = 0;

        for (Question question : studentQuestions) {

            for (Answer answerStud : question.getAnswers()) {


                for (Answer answer : questionService.getById(question.getId()).getAnswers()) {

                    if (answer.getId().equals(answerStud.getId()) && answer.getTrueness() == answerStud.getTrueness()) {
                        corrects++;
                    }

                }

            }

            if (question.getAnswers().size() == corrects) {
                correctAnswers++;

            }

            corrects = 0;
        }


        if ((correctAnswers / (double) studentQuestions.size()) * 100 < 50) {
            return 2;
        } else {
            if ((correctAnswers / (double) studentQuestions.size()) * 100 < 70) {
                return 3;
            } else {
                if ((correctAnswers / (double) studentQuestions.size()) * 100 < 85) {
                    return 4;
                } else {
                    return 5;
                }
            }
        }
    }


    public ArrayList<Question> getQuestions() {
        return studentQuestions;
    }

    public void addQuestion(Question question) {
        this.studentQuestions.add(question);
    }
}