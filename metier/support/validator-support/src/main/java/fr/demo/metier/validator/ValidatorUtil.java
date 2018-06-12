package fr.demo.metier.validator;

import fr.demo.metier.exception.ValidationException;
import fr.demo.metier.exception.CannotValidateException;
import fr.demo.metier.exception.ForbiddenException;
import fr.demo.metier.model.core.IdObject;
import fr.demo.metier.model.utils.IdObjectEnumUtils;

import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ValidatorUtil {
  
  private static final String FORBIDDEN = "FORBIDDEN";

  /**
   * Cet objet contient les données supplémentaires qui peuvent être nécessaires
   * lors des validations.
   */
  private static ThreadLocal<Map<String, Object>> validationContext = new ThreadLocal<>();
  private Validator validator;

  public static Boolean existsValidationVariable(String key) {
    return validationContext.get() != null && validationContext.get().containsKey(key);
  }

  public static <T> T getValidationVariable(String key) {
    // On utilise le containsKey pour ne pas lancer l'exception si on stocke une
    // valeur null
    if (existsValidationVariable(key)) {
      return (T) validationContext.get().get(key);
    }
    throw new CannotValidateException(key + " n'existe pas ou le context est null !");
  }

  public static <T extends Enum<T> & IdObject> T getValidationEnumVariable(Class<T> enumType, String key) {
    Object val = getValidationVariable(key);
    // On peut obtenir des BigDecimals comme des Longs
    if (val instanceof Number) {
      val = IdObjectEnumUtils.fromId(enumType, ((Number) val).longValue());
    }
    return (T) val;
  }

  /**
   * Rajoute une variable accessible pour la validation. Il faut appeler le
   * clear une fois la validation terminée.
   *
   * @param key
   * @param value
   */
  public static void addValidationVariable(String key, Object value) {
    if (validationContext.get() == null) {
      validationContext.set(new HashMap<String, Object>());
    }
    validationContext.get().put(key, value);
  }

  public static void clearValidationVariables() {
    validationContext.set(null);
  }

  public static boolean linkErrorToProperty(String property, ConstraintValidatorContext context) {
    return linkErrorToProperty(property, context, false, context.getDefaultConstraintMessageTemplate());
  }

  public static boolean linkErrorToProperty(String property, ConstraintValidatorContext context, String message) {
    return linkErrorToProperty(property, context, false, message);
  }

  public static boolean linkErrorToProperty(String property, ConstraintValidatorContext context, boolean isValid) {
    return linkErrorToProperty(property, context, isValid, context.getDefaultConstraintMessageTemplate());
  }

  public static boolean linkErrorToProperty(String property, ConstraintValidatorContext context, boolean isValid,
      String message) {
    if (!isValid) {
      // Si c'est invalide, on veut relier l'erreur sur le bon champ
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(message).addPropertyNode(property).addConstraintViolation();
    }
    return isValid;
  }

  public static Map<String, String> generateErrorMap(String property, String error) {
    Map<String, String> res = new HashMap<>();
    res.put(property, error);
    return res;
  }
  
  /**
   * Crée une erreur FORBIDDEN
   * @param context
   */
  public static void setForbidden(ConstraintValidatorContext context) {
    context.disableDefaultConstraintViolation();
    context.buildConstraintViolationWithTemplate(FORBIDDEN).addConstraintViolation();
  }
  
  public static void processErrors(Map<String, String> errors) throws ValidationException {
    if (!errors.isEmpty()) {
      for (String value: errors.values()) {
        if (FORBIDDEN.equals(value)) {
          throw new ForbiddenException();
        }
      }
      throw new ValidationException(errors);
    }
  }

  public void setValidator(Validator validator) {
    this.validator = validator;
  }

  /**
   * Valide l'objet selon les groupes passés. Rajoute automatiquement le groupe
   * Default.
   *
   * @param object
   * @param groups
   */
  public <T> Map<String, String> validate(T object, Class<?>... groups) {
    if (object == null) {
      return new HashMap<String, String>();
    }
    Class<?>[] args = Arrays.copyOf(groups, groups.length + 1);
    args[groups.length] = Default.class;
    validator.validate(object, args);
    return extractViolations(validator.validate(object, args));
  }

  /**
   * Valide l'objet selon les groupes passés. Attention, ne rajoute pas le
   * groupe Default.
   *
   * @param object
   * @param groups
   */
  public <T> Map<String, String> validateWithoutDefault(T object, Class<?>... groups) {
    if (object == null || groups == null || groups.length == 0) {
      return new HashMap<>();
    }
    return extractViolations(validator.validate(object, groups));
  }

  public Map<String, String> mergeErrors(Map<String, String> from, Map<String, String> to, String prefix) {
    for (Map.Entry<String, String> e : from.entrySet()) {
      to.put(prefix + e.getKey(), e.getValue());
    }
    return to;
  }

  private <T> Map<String, String> extractViolations(Set<ConstraintViolation<T>> violations) {
    Map<String, String> errors = new HashMap<>();
    for (ConstraintViolation<T> v : violations) {
      errors.put(v.getPropertyPath().toString(), v.getMessage());
    }
    return errors;
  }
}
