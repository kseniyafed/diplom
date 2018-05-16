package testsystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import testsystem.models.Answer;
import testsystem.models.Question;
import testsystem.models.Subject;
import testsystem.services.AnswerService;
import testsystem.services.QuestionService;
import testsystem.services.SubjectService;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/tests")
public class TestController {

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    @RequestMapping
    public String index(Model model) {
        model.addAttribute("subjects", subjectService.getSubjects());
        return "controlTestsPage";
    }

    @RequestMapping(value = "/createSubject", method = RequestMethod.POST)
    public String createSubject() {
        return "createSubjectPage";
    }

    @RequestMapping(value = "/saveSubject", method = RequestMethod.POST)
    public String saveGroup(@RequestParam("subjectName") String subjectName,
                            @RequestParam("subjectNum") String subjectNum) {
        subjectService.createSubject(subjectName, Long.valueOf(subjectNum));
        return "createSubjectPage";
    }

    @RequestMapping(value = "/deleteSubject/**", method = RequestMethod.POST)
    public void deleteGroup(HttpServletResponse response, HttpServletRequest request) throws IOException {

        String[] parseRequest = request.getServletPath().split("/");
        subjectService.deleteSubject(Long.valueOf(parseRequest[3]));
        response.sendRedirect("/tests");

    }

    @RequestMapping(value = "/editSubject/**")
    public String editSubject(Model model, HttpServletRequest request) throws IOException {

        String[] parseRequest = request.getServletPath().split("/");
        Subject subject = subjectService.getById(Long.valueOf(parseRequest[3]));

        model.addAttribute("subjectName", subject.getName());
        model.addAttribute("subjectNum", subject.getNum());
        model.addAttribute("subjectId", subject.getId());
        model.addAttribute("questions", subject.getQuestions());

        return "editSubjectPage";

    }

    @RequestMapping(value = "/saveChanges/**", method = RequestMethod.POST)
    public void saveChanges(HttpServletResponse response,
                            @RequestParam("subjectName") String subjectName,
                            @RequestParam("subjectNum") String subjectNum,
                            HttpServletRequest request) throws IOException {

        String[] parseRequest = request.getServletPath().split("/");
        subjectService.updateSubject(Long.valueOf(parseRequest[3]), subjectName, Long.valueOf(subjectNum));
        response.sendRedirect("/tests");

    }

    @RequestMapping(value = "/addQuestion/**", method = RequestMethod.POST)
    public String addQuestion(Model model, HttpServletRequest request) {
        String[] parseRequest = request.getServletPath().split("/");
        Subject subject = subjectService.getById(Long.valueOf(parseRequest[3]));
        model.addAttribute("subjectName", subject.getName());
        model.addAttribute("subjectId", subject.getId());

        return "addQuestionPage";
    }

    @RequestMapping(value = "/saveQuestion/**", method = RequestMethod.POST)
    public void saveQuestion(HttpServletResponse response,
                             Model model,
                             HttpServletRequest request,
                             @RequestParam("formulation") String formulation,
                             @RequestParam("questionNum") String questionNum) throws IOException {
        String[] parseRequest = request.getServletPath().split("/");
        Subject subject = subjectService.getById(Long.valueOf(parseRequest[3]));
        questionService.createQuestion(subject, formulation, Integer.parseInt(questionNum));
        model.addAttribute("subjectName", subject.getName());
        model.addAttribute("subjectId", subject.getId());
        response.sendRedirect("/tests/editSubject/" + subject.getId());

    }

    @RequestMapping(value = "/**/editQuestion/**")
    public String editQuestion(Model model, HttpServletRequest request) throws IOException {
        String[] parseRequest = request.getServletPath().split("/");
        Subject subject = subjectService.getById(Long.valueOf(parseRequest[2]));
        Question question = questionService.getById(Long.valueOf(parseRequest[4]));
        model.addAttribute("subjectName", subject.getName());
        model.addAttribute("subjectId", subject.getId());
        model.addAttribute("questionId", question.getId());
        model.addAttribute("questionFormulation", question.getFormulation());
        model.addAttribute("questionNum", question.getNum());
        model.addAttribute("answers", question.getAnswers());

        return "editQuestionPage";
    }

    @RequestMapping(value = "/addAnswer/**", method = RequestMethod.POST)
    public String addAnswer(Model model, HttpServletRequest request) {
        String[] parseRequest = request.getServletPath().split("/");
        Question question = questionService.getById(Long.valueOf(parseRequest[3]));
        model.addAttribute("questionFormulation", question.getFormulation());
        model.addAttribute("questionId", question.getId());
        model.addAttribute("subjectId", question.getSubject().getId());
        return "addAnswerPage";
    }

    @RequestMapping(value = "/saveAnswer/**", method = RequestMethod.POST)
    public void saveAnswer(HttpServletResponse response,
                           Model model,
                           HttpServletRequest request,
                           @RequestParam("formulation") String formulation,
                           @RequestParam(value = "trueness", required = false) String trueness) throws IOException {
        String[] parseRequest = request.getServletPath().split("/");
        Question question = questionService.getById(Long.valueOf(parseRequest[3]));

        if (trueness != null) {
            answerService.createAnswer(question, formulation, true);
        } else {
            answerService.createAnswer(question, formulation, false);
        }


        model.addAttribute("questionFormulation", question.getFormulation());
        model.addAttribute("questionId", question.getId());
        response.sendRedirect("/tests/" + question.getSubject().getId() + "/editQuestion/" + question.getId());

    }

    @RequestMapping(value = "/**/deleteAnswer/**", method = RequestMethod.POST)
    public void deleteAnswer(HttpServletResponse response, Model model, HttpServletRequest request) throws IOException {
        String[] parseRequest = request.getServletPath().split("/");
        Question question = questionService.getById(Long.valueOf(parseRequest[2]));

        answerService.deleteAnswer(Long.valueOf(parseRequest[4]));
        response.sendRedirect("/tests/" + question.getSubject().getId() + "/editQuestion/" + Long.valueOf(parseRequest[2]));
    }

    @RequestMapping(value = "/**/deleteQuestion/**", method = RequestMethod.POST)
    public void deleteQuestion(HttpServletResponse response, HttpServletRequest request) throws IOException {
        String[] parseRequest = request.getServletPath().split("/");
        questionService.deleteQuestion(Long.valueOf(parseRequest[4]));
        response.sendRedirect("/tests/editSubject/" + Long.valueOf(parseRequest[2]));
    }

    @RequestMapping(value = "/**/updateQuestion/**", method = RequestMethod.POST)
    public void updateQuestion(HttpServletResponse response,
                               HttpServletRequest request,
                               @RequestParam("questionFormulation") String formulation,
                               @RequestParam("questionNum") String questionNum) throws IOException {
        String[] parseRequest = request.getServletPath().split("/");
        questionService.updateQuestion(Long.valueOf(parseRequest[4]), formulation, Integer.parseInt(questionNum));
        response.sendRedirect("/tests/editSubject/" + Long.valueOf(parseRequest[2]));

    }

    @RequestMapping(value = "/**/editAnswer/**")
    public String editAnswer(Model model, HttpServletRequest request) throws IOException {
        String[] parseRequest = request.getServletPath().split("/");
        Question question = questionService.getById(Long.valueOf(parseRequest[2]));
        Answer answer = answerService.getById(Long.valueOf(parseRequest[4]));
        model.addAttribute("questionFormulation", question.getFormulation());
        model.addAttribute("questionId", question.getId());
        model.addAttribute("answerId", answer.getId());
        model.addAttribute("answerTrueness", answer.getTrueness());
        model.addAttribute("answerFormulation", answer.getFormulation());
        model.addAttribute("subjectId", question.getSubject().getId());


        return "editAnswerPage";
    }

    @RequestMapping(value = "/**/updateAnswer/**", method = RequestMethod.POST)
    public void updateAnswer(HttpServletResponse response,
                             HttpServletRequest request,
                             @RequestParam("answerFormulation") String formulation,
                             @RequestParam(value = "trueness", required = false) String trueness) throws IOException {
        String[] parseRequest = request.getServletPath().split("/");
        Question question = questionService.getById(Long.valueOf(parseRequest[2]));
        if (trueness != null) {
            answerService.updateAnswer(Long.valueOf(parseRequest[4]), formulation, true);
        } else {
            answerService.updateAnswer(Long.valueOf(parseRequest[4]), formulation, false);
        }

        response.sendRedirect("/tests/" + question.getSubject().getId() + "/editQuestion/" + Long.valueOf(parseRequest[2]));

    }
}
