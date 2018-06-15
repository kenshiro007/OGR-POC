package fr.demo.metier.rest.core.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ThrowableMapper implements ExceptionMapper<Throwable> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ThrowableMapper.class);

  @Override
  public Response toResponse(Throwable exception) {
    LOGGER.error("Internal server error", exception);
    return Response.status(Status.INTERNAL_SERVER_ERROR).entity(RapportExceptionFactory.rapportFactory(exception)).type(MediaType.APPLICATION_JSON_TYPE).build();
  }

}
