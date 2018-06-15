package fr.demo.metier.rest.core.provider;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

  @Override
  public Response toResponse(NotFoundException exception) {
    return Response.status(Response.Status.NOT_FOUND).entity(RapportExceptionFactory.rapportFactory(exception)).type(MediaType.APPLICATION_JSON_TYPE).build();
  }

}
