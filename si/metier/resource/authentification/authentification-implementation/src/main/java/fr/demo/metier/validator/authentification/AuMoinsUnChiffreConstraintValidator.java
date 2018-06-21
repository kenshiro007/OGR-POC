package fr.demo.metier.validator.authentification;

import fr.demo.metier.validator.constraints.authentification.AuMoinsUnChiffreConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class AuMoinsUnChiffreConstraintValidator implements ConstraintValidator<AuMoinsUnChiffreConstraint, String> {
  @Override
  public void initialize(AuMoinsUnChiffreConstraint auMoinsUnChiffreConstraint) {
    // Nothing to do
  }

  @Override
  public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
    return Pattern.compile("[0-9]").matcher(password).find();
  }
}
