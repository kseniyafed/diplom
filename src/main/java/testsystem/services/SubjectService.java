package testsystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import testsystem.models.Question;
import testsystem.models.Result;
import testsystem.models.Subject;
import testsystem.repositories.SubjectRepository;

import java.util.Iterator;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private ResultService resultService;

    @Autowired
    private QuestionService questionService;

    @Transactional
    public void createSubject(String name, Long num) {
        subjectRepository.save(new Subject(name,num));
    }


    @Transactional(readOnly = true)
    public Iterable<Subject> getSubjects() {
        return subjectRepository.findAll(sortNum());
    }

    @Transactional
    public void deleteSubject(Long id) {
        Iterable<Result> results = resultService.getResults();
        Iterator<Result> iteratorR = results.iterator();
        while (iteratorR.hasNext()) {
            Result result = iteratorR.next();
            if (result.getSubject().getId().equals(id)) {
                resultService.deleteResult(result.getId());
            }
        }
        Iterable<Question> questions = subjectRepository.findById(id).getQuestions();
        Iterator<Question> iteratorQ = questions.iterator();
        while (iteratorQ.hasNext()) {
            Question question = iteratorQ.next();
            if (question.getSubject().getId().equals(id)) {
                questionService.deleteQuestion(question.getId());
            }
        }

        subjectRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public Subject getById(Long id) {


        return subjectRepository.findById(id);
    }

    public void updateSubject(Long id, String name, Long num) {
        Subject subject = getById(id);
        subject.setName(name);
        subject.setNum(num);
        subjectRepository.save(subject);
    }

    private Sort sortNum() {
        return new Sort(Sort.Direction.ASC, "num");
    }
}
