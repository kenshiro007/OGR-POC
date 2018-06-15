package fr.demo.metier.rest.core.provider;

import fr.demo.metier.exception.WebServiceRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class WebServiceRuntimeExceptionMapper implements ExceptionMapper<WebServiceRuntimeException> {

  private static final Logger LOGGER = LoggerFactory.getLogger(WebServiceRuntimeExceptionMapper.class);

  @Override
  public Response toResponse(WebServiceRuntimeException exception) {
    LOGGER.error(exception.getMessage(), exception);
    return Response.status(Status.BAD_GATEWAY).entity(RapportExceptionFactory.rapportFactory(exception)).type(MediaType.APPLICATION_JSON_TYPE).build();
  }

}
