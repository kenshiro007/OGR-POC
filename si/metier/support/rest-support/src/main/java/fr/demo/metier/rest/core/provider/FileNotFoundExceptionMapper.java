package fr.demo.metier.rest.core.provider;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.io.FileNotFoundException;

@Provider
public class FileNotFoundExceptionMapper implements ExceptionMapper<FileNotFoundException> {

  @Override
  public Response toResponse(FileNotFoundException exception) {
    return Response.status(Response.Status.NOT_FOUND).entity(RapportExceptionFactory.rapportFactory(exception)).type(MediaType.APPLICATION_JSON_TYPE).build();
  }

}
