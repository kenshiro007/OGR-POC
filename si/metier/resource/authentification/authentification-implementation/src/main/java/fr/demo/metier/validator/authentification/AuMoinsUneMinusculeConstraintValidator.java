package fr.demo.metier.validator.authentification;

import fr.demo.metier.validator.constraints.authentification.AuMoinsUneMinusculeConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class AuMoinsUneMinusculeConstraintValidator implements ConstraintValidator<AuMoinsUneMinusculeConstraint, String> {
  @Override
  public void initialize(AuMoinsUneMinusculeConstraint auMoinsUneMinusculeConstraint) {
    // Nothing to do
  }

  @Override
  public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
    return Pattern.compile("[a-z]").matcher(password).find();
  }
}
