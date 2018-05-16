package testsystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import testsystem.models.Answer;
import testsystem.models.Question;
import testsystem.repositories.AnswerRepository;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Transactional
    public void createAnswer(Question question, String formulation, boolean trueness) {
        answerRepository.save(new Answer(question,formulation, trueness));
    }

    @Transactional
    public void deleteAnswer(Long id) {
        answerRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public Iterable<Answer> getAnswers() {
       return answerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Answer getById(Long id) {
        return answerRepository.findById(id);
    }

    @Transactional
    public void updateAnswer(Long id, String formulation, boolean trueness) {
        Answer answer = getById(id);
        answer.setFormulation(formulation);
        answer.setTrueness(trueness);
        answerRepository.save(answer);
    }
}
