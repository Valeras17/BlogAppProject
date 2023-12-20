package val.gord.blogproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import val.gord.blogproject.entity.Post;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Long> {
    //derived query methods:
    Optional<Post> findByTitle(String title);
}
