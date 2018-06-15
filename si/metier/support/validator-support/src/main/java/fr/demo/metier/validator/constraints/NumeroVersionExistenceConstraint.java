package fr.demo.metier.validator.constraints;

import fr.demo.metier.model.core.IdObject;
import fr.demo.metier.model.core.VersionObject;
import fr.demo.metier.validator.ValidatorUtil;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = NumeroVersionExistenceConstraint.NumeroVersionExistenceConstraintValidator.class)
@Documented
public @interface NumeroVersionExistenceConstraint {

  String message() default "{fr.demo.metier.validator.constraints.NumeroVersionExistenceConstraint.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  class NumeroVersionExistenceConstraintValidator
      implements
        ConstraintValidator<NumeroVersionExistenceConstraint, Object> {

    @Override
    public void initialize(NumeroVersionExistenceConstraint constraintAnnotation) {
      // Rien à faire
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
      if (obj instanceof IdObject && obj instanceof VersionObject && ((IdObject) obj).getId() != null && ((VersionObject) obj).getNumeroVersion() == null) {
        ValidatorUtil.linkErrorToProperty("numeroVersion", context);
        return false;
      }
      return true;
    }
  }

}
