package testsystem.repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import testsystem.models.State;
import testsystem.models.Subject;
import testsystem.models.User;


@Repository
public interface StateRepository extends CrudRepository<State,Long> {

    void deleteStateByUserAndSubject(User user, Subject subject);


}
