package testsystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import testsystem.models.State;
import testsystem.models.Subject;
import testsystem.models.User;
import testsystem.repositories.StateRepository;

@Service
public class StateService {

    @Autowired
    private StateRepository stateRepository;

    @Transactional
    public void createState(int position, User user, Subject subject) {
        stateRepository.deleteStateByUserAndSubject(user,subject);
        stateRepository.save(new State(position, user, subject));
    }

    @Transactional
    public void deleteState(User user, Subject subject) { stateRepository.deleteStateByUserAndSubject(user, subject);
    }

    public Iterable<State> getStateBySubject(Subject subject) {
        return stateRepository.getAllBySubject(subject);
    }

    @Transactional
    public void deleteStateByUser(User user) {
        stateRepository.deleteStateByUser(user);
    }
}
