package fr.demo.metier.model.validation.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = { })
@Documented
public @interface LimitMaxRechercheProfilConstraint {

  String message() default "{fr.demo.metier.model.validation.constraints.LimitMaxRechercheProfilConstraint.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}
