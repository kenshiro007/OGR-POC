package fr.demo.metier.rest.core.provider;

import fr.demo.metier.exception.ResourceNotFoundException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ResourceNotFoundExceptionMapper implements ExceptionMapper<ResourceNotFoundException> {

  @Override
  public Response toResponse(ResourceNotFoundException exception) {
    return Response.status(Response.Status.NOT_FOUND).entity(RapportExceptionFactory.rapportFactory(exception)).type(MediaType.APPLICATION_JSON_TYPE).build();
  }

}
