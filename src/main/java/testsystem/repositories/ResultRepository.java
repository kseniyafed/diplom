package testsystem.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import testsystem.models.Result;
import testsystem.models.Subject;
import testsystem.models.User;

@Repository
public interface ResultRepository extends CrudRepository<Result,Long> {

    void deleteResultBySubjectAndUser(Subject subject, User user);
}
