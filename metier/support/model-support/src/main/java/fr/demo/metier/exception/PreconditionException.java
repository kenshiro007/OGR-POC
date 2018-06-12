package fr.demo.metier.exception;

public class PreconditionException extends RuntimeException {

  public PreconditionException() {
  }

  public PreconditionException(String message) {
    super(message);
  }

  public PreconditionException(String message, Throwable cause) {
    super(message, cause);
  }

  public PreconditionException(Throwable cause) {
    super(cause);
  }

}
