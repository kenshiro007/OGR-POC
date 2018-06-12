package fr.demo.metier.validator.constraints;

import fr.demo.metier.exception.CannotValidateException;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Date;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = DateRangeConstraint.DateRangeConstraintValidator.class)
@Documented
public @interface DateRangeConstraint {
  
  String message() default "{fr.demo.metier.validator.constraints.DateRangeConstraint.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
  
  String start();
  
  String end();

  class DateRangeConstraintValidator implements ConstraintValidator<DateRangeConstraint, Object> {
    
    private String start;
    
    private String end;

    @Override
    public void initialize(DateRangeConstraint constraintAnnotation) {
      start = constraintAnnotation.start();
      end = constraintAnnotation.end();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
      if (object == null) {
        return true;
      }
      try {
        // get start and end fields form object
        Class<?> c = object.getClass();
        Field startField = c.getDeclaredField(start);
        Field endField = c.getDeclaredField(end);
        startField.setAccessible(true);
        endField.setAccessible(true);
        Date startDate = (Date) startField.get(object);
        Date endDate = (Date) endField.get(object);
        return startDate.before(endDate);
      } catch (Exception e) {
        throw new CannotValidateException(e);
      }
    }
  }
}
