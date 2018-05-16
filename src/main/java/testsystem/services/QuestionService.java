package testsystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import testsystem.models.Answer;
import testsystem.models.Question;
import testsystem.models.Subject;
import testsystem.repositories.QuestionRepository;

import java.util.Iterator;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerService answerService;

    @Transactional
    public void createQuestion(Subject subject, String formulation, int num) {
        questionRepository.save(new Question(subject,formulation, num));
    }

    @Transactional(readOnly = true)
    public Question getById(Long id) {
        return questionRepository.findById(id);
    }

    @Transactional
    public void deleteQuestion(Long id) {
        Iterable<Answer> answers = answerService.getAnswers();
        Iterator<Answer> iterator = answers.iterator();
        while (iterator.hasNext()) {
            Answer answer = iterator.next();
            if (answer.getQuestion().getId().equals(id)) {
                answerService.deleteAnswer(answer.getId());
            }
        }

        questionRepository.delete(id);
    }


    @Transactional(readOnly = true)
    public Iterable<Question> getQuestions() {
        return questionRepository.findAll();
    }

    @Transactional
    public void updateQuestion(Long id, String formulation, int num) {
        Question question = getById(id);
        question.setFormulation(formulation);
        question.setNum(num);
        questionRepository.save(question);
    }

    @Transactional(readOnly = true)
    public Question getByNumAndIdSubject(int num, Subject subject) {
        return  questionRepository.findByNumAndSubject(num, subject);
    }
}
