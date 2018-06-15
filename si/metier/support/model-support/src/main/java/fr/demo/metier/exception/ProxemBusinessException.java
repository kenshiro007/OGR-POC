package fr.demo.metier.exception;

public class ProxemBusinessException extends WebServiceBusinessException {
  /**
   * 
   */
  private static final long serialVersionUID = 3158804077175417736L;

  public static final String WEB_SERVICE_PROXEM = "Proxem";
  
  public static final String ANALYZE_RESUMES_OP = "analyzeResumes";
  
  public static final String ANALYZE_POSITIONS_OP = "analyzePositions";
  
  public static final String LEGAL_CONTROL_OP = "performLegalControl";

  public static final String WEB_SERVICE_DOC = "Resume";

  public static final String ENCYPTED_DOC = "documentEncrypted";

  public static final String UNREADABLE_DOC = "documentUnreadable";


  public ProxemBusinessException(String message, String operation, Object args) {
    super(message, WEB_SERVICE_PROXEM, operation, args);
  }

  public ProxemBusinessException(Throwable cause, String operation, Object args) {
    super(cause, WEB_SERVICE_PROXEM, operation, args);
  }
}
