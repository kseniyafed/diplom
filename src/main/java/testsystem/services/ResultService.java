package testsystem.services;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import testsystem.models.Result;
import testsystem.models.Subject;
import testsystem.models.User;
import testsystem.models.UserGroup;
import testsystem.repositories.ResultRepository;

import java.util.ArrayList;
import java.util.Iterator;

@Service
public class ResultService {

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private SubjectService subjectService;
    @Transactional(readOnly = true)
    public Iterable<Result> getResults() {
        return resultRepository.findAll();
    }

    @Transactional
    public void deleteResult(Long id) {
        resultRepository.delete(id);
    }

    @Transactional
    public void createResult(int mark, Subject subject, User user) {
        resultRepository.save(new Result(mark,subject, user));
    }
    @Transactional
    public void deleteResultBySubjectAndUser(Subject subject, User user) {
        resultRepository.deleteResultBySubjectAndUser(subject, user);
    }

    public ArrayList<Result> getGroupResults(UserGroup group) {
        ArrayList<Result> output= new ArrayList<Result>();
        Iterable<Subject> allSubjects = subjectService.getSubjects();
        Iterable<Result> allResults = resultRepository.findAll();
        Iterator<Subject> iteratorSub = allSubjects.iterator();
        Iterator<Result> iteratorRes = allResults.iterator();


        while (iteratorRes.hasNext()) {
            Result result = iteratorRes.next();
            if (!result.getUser().getUserGroup().equals(group)) {
                iteratorRes.remove();
            }
        }
        while (iteratorSub.hasNext()){
            Subject subject= iteratorSub.next();
            iteratorRes = allResults.iterator();
            while (iteratorRes.hasNext()){
                Result result = iteratorRes.next();
                if (result.getSubject().getId().equals(subject.getId())){
                    output.add(result);
                }else{
                    output.add(new Result(0,subject,result.getUser()));
                }

            }


        }
        return output;


    }


}
