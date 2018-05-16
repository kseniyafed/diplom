package testsystem.services;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import testsystem.models.Result;
import testsystem.models.User;
import testsystem.models.UserGroup;
import testsystem.repositories.UserRepository;

import java.util.Iterator;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResultService resultService;

    @Transactional(readOnly = true)
    public Iterable<User> getUsers() {

        return userRepository.findAll();
    }

    @Transactional
    public void createUser(String name, String surname) {
        userRepository.save(new User(name,surname));
    }

    @Transactional(readOnly = true)
    public User getByLoginAndPassword(String login, String password) {
        return userRepository.findByLoginAndPassword(login,password);

    }
    @Transactional(readOnly = true)
    public User getById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public User getByLogin(String name) {
        return userRepository.findByLogin(name);
    }

    @Transactional
    public void createUser(UserGroup userGroup, String login, String password, String name) {
        userRepository.save(new User(userGroup,login,password, name));
    }

    @Transactional
    public void deleteStudent(Long id) {
        Iterable<Result> results = resultService.getResults();
        Iterator<Result> iterator = results.iterator();
        while (iterator.hasNext()) {
            Result result = iterator.next();
            if (result.getUser().getId().equals(id)) {
                resultService.deleteResult(result.getId());
            }
        }userRepository.delete(id);
    }

    @Transactional
    public void updateUser(Long id, String name, String login, String password) {
        User user = getById(id);
        user.setName(name);
        user.setLogin(login);
        user.setPassword(password);
        userRepository.save(user);
    }


}
