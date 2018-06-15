package fr.demo.metier.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = { })
public @interface ReclamationExistenceIdConstraint {
  String message() default "{fr.demo.metier.validator.constraints.ReclamationExistenceIdConstraint.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}
