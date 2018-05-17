package testsystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import testsystem.Test;
import testsystem.models.*;
import testsystem.services.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private SubjectService subjectService;

    @Autowired
    private StateService stateService;

    @Autowired
    private QuestionService questionService;
    @Autowired
    private ResultService resultService;

    @Autowired
    private Test test;

    @Autowired
    private UserService userService;

    @RequestMapping
    public String index(Model model) {

        model.addAttribute("subjects", subjectService.getSubjects());
        return "studentPage";
    }

    @RequestMapping(value = "/subject/**")
    public String testing(Model model, HttpServletRequest request,
                          @RequestParam Map<String, String> studentAnswers) {
        String[] parseRequest = request.getServletPath().split("/");
        Subject subject = subjectService.getById(Long.valueOf(parseRequest[3]));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByLogin(auth.getName());

        stateService.createState(test.getNumber(),user,subject);
        resultService.deleteResultBySubjectAndUser(subject, user);
        List<Answer> answers = new ArrayList<Answer>();
        if (test.getNumber() != 0) {
            for (Answer answer : questionService.getByNumAndIdSubject(test.getNumber(), subject).getAnswers()) {

                if (studentAnswers.get(answer.getId().toString()) != null) {
                    answers.add(new Answer(answer.getId(),
                            answer.getFormulation(),
                            questionService.getByNumAndIdSubject(test.getNumber(), subject), true));

                } else {
                    answers.add(new Answer(answer.getId(),
                            answer.getFormulation(),
                            questionService.getByNumAndIdSubject(test.getNumber(), subject), false));

                }
            }
            test.addQuestion(new Question(
                    questionService.getByNumAndIdSubject(test.getNumber(), subject).getId(),
                    answers,
                    questionService.getByNumAndIdSubject(test.getNumber(), subject).getFormulation(),
                    questionService.getByNumAndIdSubject(test.getNumber(), subject).getNum(),
                    questionService.getByNumAndIdSubject(test.getNumber(), subject).getSubject()
            ));
        }


        if (test.getNumber() == subject.getQuestions().size()) {
            model.addAttribute("subjectId", subject.getId());

            model.addAttribute("mark", test.getMark());

            resultService.createResult(test.getMark(), subject, user);
            test.reduceNumber();
            return "resultPage";
        } else {

            test.increaseNumber();
            Question question = questionService.getByNumAndIdSubject(test.getNumber(), subject);
            model.addAttribute("questionFormulation", question.getFormulation());
            model.addAttribute("subjectName", subject.getName());
            model.addAttribute("subjectId", subject.getId());
            model.addAttribute("answers", question.getAnswers());
            model.addAttribute("step", 100/(subject.getQuestions().size()-1));
            model.addAttribute("position", (question.getNum()-1)*100/(subject.getQuestions().size()-1));

            String str = "";
            int[] positions = new int[subject.getQuestions().size()+1];

            Iterable<State> statesBySubject = stateService.getStateBySubject(subject);
            for (State state : statesBySubject) {
                positions[state.getPosition()]++;
                // state.getUser();
            }
            for (int i=0; i<positions.length; i++ ){
                str+=(i+1)+": "+positions[i]+ ", ";
            }
            System.out.println(str);


            return "subjectPage";
        }




    }


}
