package fr.demo.metier.exception;

public class WebServiceBusinessException extends Exception {

  private static final long serialVersionUID = 3158804077175417736L;

  private final String webService;
  
  private final String operation;
  
  private final transient Object args;
  
  public WebServiceBusinessException(String message, String webService, String operation, Object args) {
    super(message);
    this.webService = webService;
    this.operation = operation;
    this.args = args;
  }
  
  public WebServiceBusinessException(Throwable cause, String webService, String operation, Object args) {
    super(cause);
    this.webService = webService;
    this.operation = operation;
    this.args = args;
  }

  public String getWebService() {
    return webService;
  }

  public String getOperation() {
    return operation;
  }

  public Object getArgs() {
    return args;
  }

}
