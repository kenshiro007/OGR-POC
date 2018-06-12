package fr.demo.metier.validator.constraints;

import fr.demo.metier.validator.ValidatorUtil;
import org.apache.commons.lang.StringUtils;
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

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = AuMoinsUnConstraint.AuMoinsUnValidator.class)
@Documented
public @interface AuMoinsUnConstraint {

  String message() default "{fr.demo.metier.validator.AuMoinsUnConstraint.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  String[] fieldNames();

  String errorKey();

  class AuMoinsUnValidator implements ConstraintValidator<AuMoinsUnConstraint, Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuMoinsUnValidator.class);

    private String[] fieldNames;

    private String errorKey;

    public void initialize(AuMoinsUnConstraint constraintAnnotation) {
      this.fieldNames = constraintAnnotation.fieldNames();
      this.errorKey = constraintAnnotation.errorKey();
    }

    public boolean isValid(Object object, ConstraintValidatorContext constraintContext) {
      if (object == null || sousValidation(object)) {
        return true;
      }

      // Si une erreur est détectée, on affiche le message sur une cle virtuelle
      ValidatorUtil.linkErrorToProperty(errorKey, constraintContext);
      return false;
    }

    private boolean sousValidation(Object object) {
      try {
        for (String field : fieldNames) {
          Object property = tryGetField(object, field, "get", "is");
          if (property instanceof String) {
            if (!StringUtils.isBlank(property.toString())) {
              return true;
            }
          } else if (property != null) {
            return true;
          }
        }
      } catch (Exception e) {
        LOGGER.error("Exception during AuMoinsUnValidator validation", e);
      }
      return false;
    }

    private Object tryGetField(Object object, String field, String... prefixes) throws IllegalAccessException,
        InvocationTargetException {
      String upperCaseField = field.substring(0, 1).toUpperCase() + field.substring(1);
      for (String prefix : prefixes) {
        try {
          return object.getClass().getMethod(prefix + upperCaseField).invoke(object);
        } catch (NoSuchMethodException ignore) {
          // Rien à faire !
          LOGGER.error(ignore.getMessage(), ignore); //Sonar
        }
      }
      return null;
    }

  }
}
