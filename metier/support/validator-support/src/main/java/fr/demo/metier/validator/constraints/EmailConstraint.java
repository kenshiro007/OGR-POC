package fr.demo.metier.validator.constraints;

import org.hibernate.validator.constraints.Email;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Email(regexp = "^(?!www\\.)[-\\w\\.]+@[.|A-Za-z0-9|-]+\\.[A-Za-z0-9|-]+")
@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = { })
public @interface EmailConstraint {
  
  String message() default "{fr.demo.metier.validator.constraints.EmailConstraint.message}";

  Class<?>[] groups() default { };

  Class<? extends Payload>[] payload() default { };

}
