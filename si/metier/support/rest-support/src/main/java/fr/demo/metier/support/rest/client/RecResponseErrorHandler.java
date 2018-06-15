package fr.demo.metier.support.rest.client;

import fr.demo.metier.exception.AccesConcurrentException;
import fr.demo.metier.exception.BadRequestException;
import fr.demo.metier.exception.ForbiddenException;
import fr.demo.metier.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class RecResponseErrorHandler implements ResponseErrorHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(RecResponseErrorHandler.class);

  @Override
  public boolean hasError(ClientHttpResponse response) throws IOException {
    return hasError(getHttpStatusCode(response));
  }

  @Override
  public void handleError(ClientHttpResponse response) throws IOException {
    HttpStatus statusCode = getHttpStatusCode(response);
    LOGGER.debug("Une erreur est survenue : statut HTTP = {}", statusCode);
    switchStatusCode(response, statusCode);
    switchStatusCodeSeries(response, statusCode);
  }

  private void switchStatusCode(ClientHttpResponse response, HttpStatus statusCode) {
    switch (statusCode) {
      case NOT_FOUND:
        // TODO rajouter de l'AOP coté jahia pour transformer la ResourceNotFoundException en un return null
        throw new ResourceNotFoundException();
      case CONFLICT:
        throw new AccesConcurrentException(getContent(response));
      case FORBIDDEN:
        throw new ForbiddenException();
      case BAD_REQUEST:
        // TODO rajouter de l'AOP coté jahia pour transformer l'exception BadRequestException en ValidationException, les lignes qui suivent permettent de deserialiser le json en map d'erreurs
        // ObjectMapper mapper = new ObjectMapper();
        // Map<String, String> errors =
        // mapper.readValue(getResponseBody(response), new
        // TypeReference<HashMap<String, String>>() {});
        // throw new ValidationException(errors);
        throw new BadRequestException(getContent(response));
      default:
    }
  }

  private void switchStatusCodeSeries(ClientHttpResponse response, HttpStatus statusCode) throws IOException {
    switch (statusCode.series()) {
      case CLIENT_ERROR:
        throw new HttpClientErrorException(statusCode, response.getStatusText(), response.getHeaders(), getResponseBody(response), getCharset(response));
      case SERVER_ERROR:
        throw new HttpServerErrorException(statusCode, response.getStatusText(), response.getHeaders(), getResponseBody(response), getCharset(response));
      default:
        throw new RestClientException("Unknown status code [" + statusCode + "]");
    }
  }

  protected boolean hasError(HttpStatus statusCode) {
    return statusCode.series() == HttpStatus.Series.CLIENT_ERROR || statusCode.series() == HttpStatus.Series.SERVER_ERROR;
  }

  private String getContent(ClientHttpResponse response) {
    byte[] data = getResponseBody(response);
    Charset charset = getCharset(response);
    return charset != null ? new String(data, charset) : new String(data);
  }

  private HttpStatus getHttpStatusCode(ClientHttpResponse response) throws IOException {
    try {
      return response.getStatusCode();
    } catch (IllegalArgumentException ex) {
      LOGGER.error(ex.getMessage(), ex); //Sonar
      throw new UnknownHttpStatusCodeException(
        response.getRawStatusCode(),
        response.getStatusText(),
        response.getHeaders(),
        getResponseBody(response),
        getCharset(response));
    }
  }

  private byte[] getResponseBody(ClientHttpResponse response) {
    try {
      InputStream responseBody = response.getBody();
      if (responseBody != null) {
        return FileCopyUtils.copyToByteArray(responseBody);
      }
    } catch (IOException ex) {
      // ignore
      LOGGER.error(ex.getMessage(), ex); //Sonar
    }
    return new byte[0];
  }

  private Charset getCharset(ClientHttpResponse response) {
    HttpHeaders headers = response.getHeaders();
    MediaType contentType = headers.getContentType();
    return contentType != null ? contentType.getCharSet() : null;
  }

}
