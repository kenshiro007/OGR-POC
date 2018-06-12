package fr.demo.metier.validator.constraints;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Calendar;
import java.util.Date;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = AgeConstraint.AgeConstraintValidator.class)
@Documented
public @interface AgeConstraint {
  
  String message() default "{fr.demo.metier.validator.constraints.AgeConstraint.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
  
  int min() default Integer.MIN_VALUE;
  
  int max() default Integer.MAX_VALUE;

  class AgeConstraintValidator implements ConstraintValidator<AgeConstraint, Date> {
    
    private int min;
    
    private int max;

    @Override
    public void initialize(AgeConstraint constraintAnnotation) {
      min = constraintAnnotation.min();
      max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(Date value, ConstraintValidatorContext context) {
      if (value == null) {
        return true;
      }
      Calendar dateNaissance = Calendar.getInstance();
      dateNaissance.setTime(value);
      Calendar today = Calendar.getInstance();
      int age = today.get(Calendar.YEAR) - dateNaissance.get(Calendar.YEAR);
      if (today.get(Calendar.DAY_OF_YEAR) < dateNaissance.get(Calendar.DAY_OF_YEAR)) {  
        --age;
      }
      return min <= age && age <= max;
    }

  }

}
