package fr.demo.metier.rest.core.provider;

import javax.ws.rs.NotAllowedException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotAllowedExceptionMapper implements ExceptionMapper<NotAllowedException> {

  @Override
  public Response toResponse(NotAllowedException exception) {
    return Response.status(Status.METHOD_NOT_ALLOWED).entity(RapportExceptionFactory.rapportFactory(exception)).type(MediaType.APPLICATION_JSON_TYPE).build();
  }

}
