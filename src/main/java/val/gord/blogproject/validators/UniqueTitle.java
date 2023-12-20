package val.gord.blogproject.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)// we need to check in runtime
@Constraint(validatedBy = {UniqueTitleValidator.class})// class for validator
public @interface UniqueTitle {
    String message()default "Post Title must be unique";
// enable validation groups
    Class<?>[] groups() default {};
// the annotation stores a payload
// the field value is stored in the payload
    Class<? extends Payload>[] payload() default {};


}
