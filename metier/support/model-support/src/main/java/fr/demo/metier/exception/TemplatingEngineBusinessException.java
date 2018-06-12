package fr.demo.metier.exception;

public class TemplatingEngineBusinessException extends WebServiceBusinessException {
  public static final long serialVersionUID = 20141106145604L;

  public static final String WEB_SERVICE_TEMPLATING = "Velocity templating service";

  public TemplatingEngineBusinessException(String message, String operation, Object args) {
    super(message, WEB_SERVICE_TEMPLATING, operation, args);
  }

}