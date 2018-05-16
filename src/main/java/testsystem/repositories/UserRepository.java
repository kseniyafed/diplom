package testsystem.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import testsystem.models.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User,Long> {

    User findByLoginAndPassword(@Param("login") String login,
                                @Param("password") String password);

    User findById(@Param("id") Long id);

    User findByLogin(String login);

}


//@Query("select u from User u where u.password = :password and u.login = :login")