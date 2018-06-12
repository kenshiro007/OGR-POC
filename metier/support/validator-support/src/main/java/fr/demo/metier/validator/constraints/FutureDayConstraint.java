package fr.demo.metier.validator.constraints;

import org.apache.commons.lang.time.DateUtils;

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

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = FutureDayConstraint.FutureDayConstraintValidator.class)
@Documented
public @interface FutureDayConstraint {

  String message() default "{fr.demo.metier.validator.constraints.FutureDayConstraint.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  int amount() default 0;

  @Target({FIELD})
  @Retention(RUNTIME)
  @Documented
  @interface List {
    FutureDayConstraint[] value();
  }

  class FutureDayConstraintValidator implements ConstraintValidator<FutureDayConstraint, Date> {

    private int amount;

    @Override
    public void initialize(FutureDayConstraint constraintAnnotation) {
      this.amount = constraintAnnotation.amount();
    }

    @Override
    public boolean isValid(Date value, ConstraintValidatorContext context) {
      if (value == null) {
        return true;
      }
      Date dateDebut = DateUtils.truncate(DateUtils.addDays(new Date(), this.amount), Calendar.DAY_OF_MONTH);
      return dateDebut.equals(value) || dateDebut.before(value);
    }

  }

}
