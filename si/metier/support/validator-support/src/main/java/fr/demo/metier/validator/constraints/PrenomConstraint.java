package fr.demo.metier.validator.constraints;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Pattern(regexp = "^([a-zA-ZÀÁÂÃÄÅàáâãäåÒÓÔÕÖØòóôõöøÈÉÊËèéêëÇçÌÍÎÏìíîïÙÚÛÜùúûüÿÑñ' ]+[-]?)*" +
        "[a-zA-ZÀÁÂÃÄÅàáâãäåÒÓÔÕÖØòóôõöøÈÉÊËèéêëÇçÌÍÎÏìíîïÙÚÛÜùúûüÿÑñ' ]+$")
@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = PrenomConstraint.CheackBaliseHtmlInjection.class)
public @interface PrenomConstraint {
    String message() default "{fr.demo.metier.validator.constraints.PrenomConstraint.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class CheackBaliseHtmlInjection implements ConstraintValidator<PrenomConstraint, String> {

        @Override
        public void initialize(PrenomConstraint constraintAnnotation) {

        }

        @Override
        public boolean isValid(String champValue, ConstraintValidatorContext context) {
            if (champValue == null || "".equals(champValue.trim())) {
                return true;
            }

            return !(champValue.contains("<") || champValue.contains(">"));
        }

    }
}
