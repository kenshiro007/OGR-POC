package fr.demo.metier.validator.authentification;

import fr.demo.metier.validator.constraints.authentification.PasswordSizeConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created on 23/03/2015
 * For si
 */
public class PasswordSizeConstraintValidator implements ConstraintValidator<PasswordSizeConstraint, String> {

  public static final int MIN = 12;
  public static final int MAX = 30;

  @Override
  public void initialize(PasswordSizeConstraint passwordSizeConstraint) {
    // Nothing to do
  }

  @Override
  public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
    return password != null && password.length() >= MIN && password.length() <= MAX;
  }
}
