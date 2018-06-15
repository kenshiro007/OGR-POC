package fr.demo.metier.support.rest.client;

import fr.demo.metier.exception.AccesConcurrentException;
import fr.demo.metier.exception.ValidationException;
import fr.demo.metier.exception.ForbiddenException;

import javax.ws.rs.core.Response;
import java.io.FileNotFoundException;
import java.util.Map;

public class JerseyExceptionMapper {

  private JerseyExceptionMapper() {
    super();
  }

  public static <T> T toResponseDto(Response response, Class<T> businessDtoType) {

    Response.Status[] statusOK = { Response.Status.OK, Response.Status.CREATED };
    for (Response.Status status : statusOK) {
      if (status.getStatusCode() == response.getStatus()) {
        return response.readEntity(businessDtoType);
      }
    }
    return null;
  }

  public static <T> T throwsValidationException(Response response, T responseDto) throws ValidationException {

    if (response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode()) {
      throw new ValidationException(response.readEntity(Map.class));
    } else if (response.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
      throw new ForbiddenException();
    } else if (response.getStatus() == Response.Status.CONFLICT.getStatusCode()) {
      throw new AccesConcurrentException(response.getStatusInfo().toString());
    } else {
      return responseDto;
    }
  }

  public static <T> T throwsFileNotFoundException(Response response, T responseDto) throws FileNotFoundException {

    if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
      throw new FileNotFoundException();
    }
    return responseDto;
  }

}
