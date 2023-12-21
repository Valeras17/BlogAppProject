package val.gord.blogproject.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import val.gord.blogproject.entity.User;
import val.gord.blogproject.repository.UserRepository;
import java.util.Optional;

@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    private final UserRepository userRepository;
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        Optional<User> user = userRepository.findByEmailIgnoreCase(email);
        //if user does not exist -> VALID
        return user.isEmpty();
    }
}
