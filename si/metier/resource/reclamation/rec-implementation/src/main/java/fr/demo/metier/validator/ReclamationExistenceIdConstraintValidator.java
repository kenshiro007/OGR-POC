package fr.demo.metier.validator;

import fr.demo.metier.service.ReclamationService;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ReclamationExistenceIdConstraintValidator implements ConstraintValidator<ReclamationExistenceIdConstraint, Long> {

  @Resource(name = "reclamationService")
  private ReclamationService reclamationService;

  @Override
  public void initialize(ReclamationExistenceIdConstraint constraintAnnotation) {
    // Rien à faire
  }

  @Override
  public boolean isValid(Long reclamationId, ConstraintValidatorContext context) {
    if (reclamationId == null) {
      return true;
    }
    return reclamationService.exists(reclamationId);
  }

}
