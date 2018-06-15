package fr.demo.metier.rest.core.provider;

import fr.demo.metier.exception.ValidationException;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Map;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {

  @Context
  HttpHeaders headers;

  @Override
  public Response toResponse(ValidationException exception) {
    if(headers.getMediaType().toString().equals(MediaType.APPLICATION_XML)){
      ErreursValidation erreursValidation = new ErreursValidation();
      for (Map.Entry<String, String> erreur : exception.getErrors().entrySet())
      {
        XMLError xmlError = new XMLError(erreur.getKey(),erreur.getValue());
        erreursValidation.getErreur().add(xmlError);
      }
      return Response.status(Status.BAD_REQUEST).entity(erreursValidation).type(MediaType.APPLICATION_XML).build();
    }else{
      return Response.status(Status.BAD_REQUEST).entity(exception.getErrors()).type(MediaType.APPLICATION_JSON).build();
    }
  }

}
