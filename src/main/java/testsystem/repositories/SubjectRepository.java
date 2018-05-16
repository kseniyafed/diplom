package testsystem.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import testsystem.models.Subject;

@Repository
public interface SubjectRepository extends CrudRepository<Subject,Long> {
    Subject findById(Long id);

    Iterable<Subject> findAll(Sort orders);
}
