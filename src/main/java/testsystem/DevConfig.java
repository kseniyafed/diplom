package testsystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import testsystem.models.User;
import testsystem.models.UserGroup;
import testsystem.repositories.UserGroupRepository;
import testsystem.repositories.UserRepository;

import javax.annotation.PostConstruct;

@Component
public class DevConfig {

    @Autowired private UserRepository userRepository;
    @Autowired private UserGroupRepository userGroupRepository;

    @PostConstruct
    public void init() {
        if(userRepository.count() == 0) {
            UserGroup adminGroup = new UserGroup();
            adminGroup.setName("ADMIN");
            userGroupRepository.save(adminGroup);

            User user = new User();
            user.setName("admin");
            user.setLogin("admin");
            user.setPassword("admin");
            user.setUserGroup(adminGroup);
            userRepository.save(user);
        }
    }
}
