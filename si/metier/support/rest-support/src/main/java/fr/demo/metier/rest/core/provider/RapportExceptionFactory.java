package fr.demo.metier.rest.core.provider;

import fr.demo.metier.exception.*;
import fr.demo.metier.model.core.RapportExceptionDto;
import org.apache.commons.lang.exception.ExceptionUtils;

import javax.ws.rs.NotFoundException;
import java.io.FileNotFoundException;

public class RapportExceptionFactory {

  private RapportExceptionFactory() {
  }
  
  public static RapportExceptionDto rapportFactory(Throwable throwable) {
    return new RapportExceptionDto(throwable.getMessage(), getStackTrace(throwable));
  }
  
  public static RapportExceptionDto rapportFactory(ForbiddenException exception) {
    return new RapportExceptionDto(exception.getMessage(), null);
  }
  
  public static RapportExceptionDto rapportFactory(WebServiceBusinessException exception) {
    return new RapportExceptionDto(exception.getMessage(), getStackTrace(exception),
        exception.getWebService(), exception.getOperation(), exception.getArgs());
  }
  
  public static RapportExceptionDto rapportFactory(WebServiceRuntimeException exception) {
    return new RapportExceptionDto(exception.getMessage(), getStackTrace(exception),
        exception.getWebService(), exception.getOperation(), exception.getArgs());
  }
  
  public static RapportExceptionDto rapportFactory(ResourceNotFoundException exception) {
    return new RapportExceptionDto(exception.getMessage() + " - ENTITY", null);
  }
  
  public static RapportExceptionDto rapportFactory(NotFoundException exception) {
    return new RapportExceptionDto(exception.getMessage() + " - URL", null);
  }
  
  public static RapportExceptionDto rapportFactory(FileNotFoundException exception) {
    return new RapportExceptionDto(exception.getMessage(), null);
  }
  
  public static RapportExceptionDto rapportFactory(AccesConcurrentException exception) {
    return new RapportExceptionDto(exception.getMessage(), getStackTrace(exception),
        exception.getCanonicalName(), exception.getIdObject());
  }
  
  private static String getStackTrace(Throwable throwable) {
    return ExceptionUtils.getStackTrace(throwable);
  }
  
}
