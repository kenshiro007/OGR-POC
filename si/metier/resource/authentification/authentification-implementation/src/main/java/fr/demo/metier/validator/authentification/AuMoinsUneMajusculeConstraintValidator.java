package fr.demo.metier.validator.authentification;

import fr.demo.metier.validator.constraints.authentification.AuMoinsUneMajusculeConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class AuMoinsUneMajusculeConstraintValidator implements ConstraintValidator<AuMoinsUneMajusculeConstraint, String> {
  @Override
  public void initialize(AuMoinsUneMajusculeConstraint auMoinsUneMajusculeConstraint) {
    // Nothing to do
  }

  @Override
  public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
    return Pattern.compile("[A-Z]").matcher(password).find();
  }
}
