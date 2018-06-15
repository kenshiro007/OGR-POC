package fr.demo.metier.rest.core.provider;

import fr.demo.metier.exception.BadRequestException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BadRequestExceptionMapper implements ExceptionMapper<BadRequestException> {

  @Override
  public Response toResponse(BadRequestException exception) {
    return Response.status(Response.Status.BAD_REQUEST).entity(exception.getMessage()).type(MediaType.APPLICATION_JSON_TYPE).build();
  }

}
