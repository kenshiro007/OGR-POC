package fr.demo.metier.service.security.authentication;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import fr.demo.metier.service.security.utils.AuthenticationUtils;
import fr.demo.metier.service.security.utils.SsoAuthenticationUtils;

public final class TokenAuthenticationFilter extends GenericFilterBean {

  private static final Logger LOGGER = LoggerFactory.getLogger(TokenAuthenticationFilter.class);

  private static final String REQUEST_ATTR_DO_NOT_CONTINUE = "TokenAuthenticationFilter-doNotContinue";

  @Resource(name = "ssoAuthenticationUtils")
  SsoAuthenticationUtils ssoAuthenticationUtils;

  private final RestAuthenticationService restAuthenticationService;

  public TokenAuthenticationFilter(RestAuthenticationService restAuthenticationService) {
    this.restAuthenticationService = restAuthenticationService;
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    LOGGER.debug("doFilter");
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;
    checkToken(httpRequest, httpResponse);
    if (canRequestProcessingContinue(httpRequest) && "POST".equals(httpRequest.getMethod())) {
      checkLogin(httpRequest, httpResponse);
    }
    if (canRequestProcessingContinue(httpRequest)) {
      chain.doFilter(request, response);
    }
    LOGGER.debug("Authentification : {}", SecurityContextHolder.getContext().getAuthentication());
  }

  private void checkLogin(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException {

    LOGGER.debug("checkLogin");
    String username = httpRequest.getHeader(AuthenticationUtils.HEADER_USERNAME);
    String password = httpRequest.getHeader(AuthenticationUtils.HEADER_PASSWORD);
    if (username != null && password != null) {
      checkUsernameAndPassword(username, password, httpResponse);
      doNotContinueWithRequestProcessing(httpRequest);
    } else {
      username = httpRequest.getHeader(SsoAuthenticationUtils.SSO_HEADER_USERNAME);
      password = httpRequest.getHeader(SsoAuthenticationUtils.SSO_HEADER_PASSWORD);
      if (username != null && password != null) {
        checkSsoUsernameAndPassword(
            ssoAuthenticationUtils.computePrincipalForSsoAuthentication(username),
            password,
            httpResponse);
        doNotContinueWithRequestProcessing(httpRequest);
      }
    }
  }

  private void checkUsernameAndPassword(String username, String password, HttpServletResponse httpResponse)
      throws IOException {

    LOGGER.debug("checkUsernameAndPassword");
    String token = restAuthenticationService.computeValidToken(username, password);
    if (token != null) {
      httpResponse.setHeader(AuthenticationUtils.HEADER_TOKEN, token);
    } else {
      httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
  }

  private void checkSsoUsernameAndPassword(String username, String password, HttpServletResponse httpResponse)
      throws IOException {

    LOGGER.debug("checkSsoUsernameAndPassword");
    String token = restAuthenticationService.computeValidSsoToken(username, password);
    if (token != null) {
      httpResponse.setHeader(SsoAuthenticationUtils.SSO_HEADER_TOKEN, token);
    } else {
      httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
  }

  private void checkToken(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException {

    String token = httpRequest.getHeader(AuthenticationUtils.HEADER_TOKEN);
    if (token != null && !token.isEmpty()) {
      if (!restAuthenticationService.checkToken(token)) {
        LOGGER.debug(AuthenticationUtils.HEADER_TOKEN + " invalide : {}", token);
        httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        doNotContinueWithRequestProcessing(httpRequest);
      }
    } else {
      token = httpRequest.getHeader(SsoAuthenticationUtils.SSO_HEADER_TOKEN);
      if (token != null && !token.isEmpty()) {
        if (!restAuthenticationService.checkSsoToken(token)) {
          LOGGER.debug(SsoAuthenticationUtils.SSO_HEADER_TOKEN + " invalide : {}", token);
          httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
          doNotContinueWithRequestProcessing(httpRequest);
        }
      }
    }
  }

  private void doNotContinueWithRequestProcessing(HttpServletRequest httpRequest) {

    httpRequest.setAttribute(REQUEST_ATTR_DO_NOT_CONTINUE, "");
  }

  private boolean canRequestProcessingContinue(HttpServletRequest httpRequest) {

    return httpRequest.getAttribute(REQUEST_ATTR_DO_NOT_CONTINUE) == null;
  }

}
