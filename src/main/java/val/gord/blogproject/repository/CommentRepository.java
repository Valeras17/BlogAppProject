package val.gord.blogproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import val.gord.blogproject.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    //Derived Query Methods:
    List<Comment> findCommentsByPostId(long postId);
    List<Comment> findCommentsByEmailAndUsername(String email,String username);
}
