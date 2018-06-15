package fr.demo.metier.rest.core.provider;

import fr.demo.metier.exception.PreconditionException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class PreconditionExceptionMapper implements ExceptionMapper<PreconditionException> {

  @Override
  public Response toResponse(PreconditionException exception) {
    return Response.status(Status.PRECONDITION_FAILED).entity(RapportExceptionFactory.rapportFactory(exception)).type(MediaType.APPLICATION_JSON_TYPE).build();
  }

}
