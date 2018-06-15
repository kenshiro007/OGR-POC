package fr.demo.metier.validator.constraints;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = RechercheCritereConstraint.RechercheCritereConstraintValidator.class)
@Documented
public @interface RechercheCritereConstraint {

  String message() default "{fr.demo.metier.validator.constraints.RechercheCritereConstraint.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  String[] ignores() default {};

  class RechercheCritereConstraintValidator
      implements
        ConstraintValidator<RechercheCritereConstraint, Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RechercheCritereConstraintValidator.class);

    private List<String> ignores;

    @Override
    public void initialize(RechercheCritereConstraint constraintAnnotation) {
      this.ignores = Arrays.asList(constraintAnnotation.ignores());
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
      if (value == null) {
        return true;
      }
      try {
        for (Field f : value.getClass().getDeclaredFields()) {
          if (!ignores.contains(f.getName()) && isFieldOk(value, f)) {
            return true;
          }
        }
      } catch (IllegalArgumentException | IllegalAccessException e) {
        LOGGER.error("Champ inaccessible dans la classe " + value.getClass().getCanonicalName() + " !", e);
      }
      return false;
    }
    
    private boolean isFieldOk(Object value, Field f) throws IllegalAccessException {
      f.setAccessible(true);
      if (f.get(value) != null) {
        if (Collection.class.isAssignableFrom(f.getType())) {
          Collection<?> coll = (Collection<?>) f.get(value);
          if(!coll.isEmpty()) {
            return true;
          }
        } else {
          return true;
        }
      }
      return false;
    }

  }

}
