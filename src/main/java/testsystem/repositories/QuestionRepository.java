package testsystem.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import testsystem.models.Question;
import testsystem.models.Subject;

@Repository
public interface QuestionRepository extends CrudRepository<Question,Long> {
    Question findById(Long id);
    Question findByNumAndSubject(int num, Subject subject);
}
