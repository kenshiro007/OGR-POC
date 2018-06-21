package fr.demo.metier.validator.authentification;

import fr.demo.metier.validator.constraints.authentification.AuMoinsUnSpecialConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class AuMoinsUnSpecialConstraintValidator implements ConstraintValidator<AuMoinsUnSpecialConstraint, String> {
  @Override
  public void initialize(AuMoinsUnSpecialConstraint auMoinsUnSpecialConstraint) {
    // Nothing to do
  }

  @Override
  public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
    return Pattern.compile("[^A-z0-9]|[_]").matcher(password).find();
  }
}
