package val.gord.blogproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import val.gord.blogproject.entity.Role;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    //derived query methods:

    Optional<Role> findByNameIgnoreCase(String name);

}
