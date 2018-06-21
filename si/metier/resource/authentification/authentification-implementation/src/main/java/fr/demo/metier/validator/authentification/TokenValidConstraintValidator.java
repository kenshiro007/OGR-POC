package fr.demo.metier.validator.authentification;

import fr.demo.metier.service.authentification.AuthentificationService;
import fr.demo.metier.validator.constraints.authentification.TokenValidConstraint;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TokenValidConstraintValidator implements ConstraintValidator<TokenValidConstraint, String> {

  @Resource(name = "authentificationService")
  private AuthentificationService authentificationService;

  @Override
  public void initialize(TokenValidConstraint tokenValidConstraint) {
    // Nothing to do
  }

  @Override
  public boolean isValid(String token, ConstraintValidatorContext constraintValidatorContext) {
    return token == null || StringUtils.isEmpty(token) || authentificationService.isTokenCadreValid(token);
  }
}
