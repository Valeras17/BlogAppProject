package val.gord.blogproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import val.gord.blogproject.entity.Post;
import val.gord.blogproject.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //get users by:
    Optional<User> findByUsernameIgnoreCaseOrEmailIgnoreCase(String username, String email);
    Optional<User> findByUsernameIgnoreCase(String username);
    Optional<User> findByEmailIgnoreCase(String email);

    //check if a user exists for validation purposes:
    Boolean existsUserByUsernameIgnoreCase(String username);
    Boolean existsUserByEmailIgnoreCase(String username);
}
