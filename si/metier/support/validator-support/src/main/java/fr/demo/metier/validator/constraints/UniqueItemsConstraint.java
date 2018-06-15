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
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = UniqueItemsConstraint.UniqueItemsConstraintValidator.class)
@Documented
public @interface UniqueItemsConstraint {

  String message() default "{fr.demo.metier.validator.UniqueItemsConstraint.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  String name();

  class UniqueItemsConstraintValidator implements ConstraintValidator<UniqueItemsConstraint, List<?>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UniqueItemsConstraintValidator.class);

    private String name;

    public void initialize(UniqueItemsConstraint constraintAnnotation) {
      this.name = constraintAnnotation.name();
    }

    public boolean isValid(List<?> list, ConstraintValidatorContext constraintContext) {
      if (list == null || list.isEmpty() || sousValidation(list)) {
        return true;
      }
      // Si une erreur est détectée, on affiche le message sur une cle virtuelle
//      ValidatorUtil.linkErrorToProperty(errorKey, constraintContext);
      return false;
    }

    private boolean sousValidation(List<?> list) {
      List<Object> objs = new ArrayList<>(list.size());
      for (Object o: list) {
        if (!objs.contains(tryGetField(o, name, "get", "is"))) {
          objs.add(tryGetField(o, name, "get", "is"));
        }else{
          return false;
        }
      }
      return true;
    }

    private Object tryGetField(Object object, String field, String... prefixes) {
      String upperCaseField = field.substring(0, 1).toUpperCase() + field.substring(1);
      for (String prefix : prefixes) {
        try {
          return object.getClass().getMethod(prefix + upperCaseField).invoke(object);
        } catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
            | SecurityException ignore) {
          LOGGER.error(ignore.getMessage(), ignore); // Sonar
        }
      }
      return null;
    }
  }
}
