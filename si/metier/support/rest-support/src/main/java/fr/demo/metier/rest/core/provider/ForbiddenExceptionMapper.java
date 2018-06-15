package fr.demo.metier.rest.core.provider;

import fr.demo.metier.exception.ForbiddenException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ForbiddenExceptionMapper implements ExceptionMapper<ForbiddenException> {

  @Override
  public Response toResponse(ForbiddenException exception) {
    return Response.status(Status.FORBIDDEN).entity(RapportExceptionFactory.rapportFactory(exception)).type(MediaType.APPLICATION_JSON_TYPE).build();
  }

}
