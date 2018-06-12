package fr.demo.metier.exception;


import java.util.Map;


public class ValidationException extends RuntimeException {

  private Map<String, String> errors;

  public ValidationException(Map<String, String> errors) {
    this.errors = errors;
  }

  public Map<String, String> getErrors() {
    return errors;
  }
    
  @Override
  public String getMessage() {
    if (errors == null || errors.isEmpty()) {
      return super.getMessage();
    }
    return errors.toString();
  }
  
}
