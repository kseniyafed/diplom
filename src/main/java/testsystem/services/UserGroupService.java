package testsystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import testsystem.models.User;
import testsystem.models.UserGroup;
import testsystem.repositories.UserGroupRepository;

import java.util.Iterator;


@Service
public class UserGroupService {


    @Autowired
    private UserGroupRepository userGroupRepository;

    @Autowired
    private UserService userService;

    @Transactional(readOnly = true)
    public Iterable<UserGroup> getGroups() {
        Iterable<UserGroup> groups = userGroupRepository.findAll();
        Iterator<UserGroup> iterator = groups.iterator();
        while (iterator.hasNext()) {
            UserGroup group = iterator.next();
            if (group.getName().equals("ADMIN")) {
                iterator.remove();
            }
        }

        return groups;
    }

    @Transactional
    public void createGroup(String name) {
        userGroupRepository.save(new UserGroup(name));
    }

    @Transactional
    public void deleteGroup(Long id) {
        Iterable<User> users = userService.getUsers();
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getUserGroup().getId().equals(id)) {
                userService.deleteStudent(user.getId());
            }
        }

        userGroupRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public UserGroup getById(Long id) {
        return userGroupRepository.findById(id);
    }

    @Transactional
    public void updateGroup(Long id, String groupName) {
        UserGroup userGroup = getById(id);
        userGroup.setName(groupName);
        userGroupRepository.save(userGroup);
    }
}
