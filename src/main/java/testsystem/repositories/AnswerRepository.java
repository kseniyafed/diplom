package testsystem.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import testsystem.models.Answer;

@Repository
public interface AnswerRepository extends CrudRepository<Answer,Long> {
    Answer findById(Long id);
}
