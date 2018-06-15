package fr.demo.metier.validator.constraints;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = ValeurParDefautInvalideConstraint.ValeurParDefautInvalideConstraintValidator.class)
@Documented
public @interface ValeurParDefautInvalideConstraint {

  String message() default "{fr.demo.metier.validator.constraints.ValeurParDefautInvalideConstraint.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
  
  String valeurParDefaut() default "";

  class ValeurParDefautInvalideConstraintValidator implements ConstraintValidator<ValeurParDefautInvalideConstraint, String> {
    
    private String valeurParDefaut;

    @Override
    public void initialize(ValeurParDefautInvalideConstraint constraintAnnotation) {
      this.valeurParDefaut = constraintAnnotation.valeurParDefaut();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
      return value == null || !valeurParDefaut.equals(value);
    }
  }
}
