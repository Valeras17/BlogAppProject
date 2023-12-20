package val.gord.blogproject.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import val.gord.blogproject.entity.Post;
import val.gord.blogproject.repository.PostRepository;

import java.util.Optional;

@RequiredArgsConstructor
public class UniqueTitleValidator implements ConstraintValidator<UniqueTitle,String> {

    private final PostRepository postRepository;


    @Override
    public boolean isValid(String title, ConstraintValidatorContext constraintValidatorContext) {
        Optional<Post> post = postRepository.findByTitle(title);
        //if post  with same title does not exist -->GOOD!
        return post.isEmpty();
    }
}
