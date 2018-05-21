package testsystem.repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import testsystem.models.UserGroup;


@Repository
public interface UserGroupRepository extends CrudRepository<UserGroup, Long> {

    UserGroup findById(Long id);
}
