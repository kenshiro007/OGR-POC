package fr.demo.metier.rest.core.provider;

import fr.demo.metier.exception.AccesConcurrentException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AccesConcurrentExceptionMapper implements ExceptionMapper<AccesConcurrentException> {

  @Override
  public Response toResponse(AccesConcurrentException exception) {
    return Response.status(Status.CONFLICT).entity(RapportExceptionFactory.rapportFactory(exception)).type(MediaType.APPLICATION_JSON_TYPE).build();
  }

}
