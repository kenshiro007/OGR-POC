package fr.demo.metier.exception;

public class CannotValidateException extends RuntimeException {

  public CannotValidateException() {
  }

  public CannotValidateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public CannotValidateException(String message, Throwable cause) {
    super(message, cause);
  }

  public CannotValidateException(String message) {
    super(message);
  }

  public CannotValidateException(Throwable cause) {
    super(cause);
  }

}
