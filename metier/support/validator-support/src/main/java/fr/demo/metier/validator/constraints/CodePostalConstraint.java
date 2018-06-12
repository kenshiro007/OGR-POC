package fr.demo.metier.validator.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Pattern(regexp = "^[\\w\\-\\s]+$", message = CodePostalConstraint.CODE_POSTAL_FORMAT_MESSAGE)
@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = { })
public @interface CodePostalConstraint {

  String CODE_POSTAL_FORMAT_MESSAGE = "{fr.demo.metier.validator.constraints.CodePostalConstraint.message}";

  String CODE_POSTAL_VALUE_MESSAGE = "{fr.demo.metier.validator.constraints.CodePostalConstraint.value.message}";
  
  String message() default CODE_POSTAL_FORMAT_MESSAGE;

  Class<?>[] groups() default { };

  Class<? extends Payload>[] payload() default { };

}
