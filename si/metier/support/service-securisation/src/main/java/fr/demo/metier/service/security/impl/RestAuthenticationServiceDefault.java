package fr.demo.metier.service.security.impl;

import fr.demo.metier.service.security.authentication.RestAuthenticationService;
import fr.demo.metier.service.security.authentication.TokenManager;
import fr.demo.metier.service.security.dao.UserDao;
import fr.demo.metier.service.security.domain.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.StringTokenizer;

public class RestAuthenticationServiceDefault implements RestAuthenticationService {

  private static final Logger LOGGER = LoggerFactory.getLogger(RestAuthenticationServiceDefault.class);

  public static final String TOKEN_RECO_PASSIVE_KEYWORD = "token.reco.passive";

  public static final String TOKEN_GENERIQUE_KEYWORD = "token.generique";

  private static final String[] USERS_GEN_SANS_AUTHENT = { "USER_ADEP", "VISI_FRONT", "INTRANET" };

  @Autowired
  private ApplicationContext applicationContext;

  private final AuthenticationManager authenticationManager;

  @Autowired
  private UserDao userDao;

  private final TokenManager tokenManager;

  public RestAuthenticationServiceDefault(AuthenticationManager authenticationManager, TokenManager tokenManager) {
    this.authenticationManager = authenticationManager;
    this.tokenManager = tokenManager;
  }

  @PostConstruct
  public void init() {

    LOGGER.debug("AuthenticationServiceImpl.init with: {}", applicationContext);
  }

  @Override
  public String computeValidToken(String principal, String motDePasse) {

    LOGGER.debug("computeValidToken({}, [PROTECTED])", principal);
    Authentication authentication = new UsernamePasswordAuthenticationToken(principal, motDePasse);
    try {
      authentication = authenticationManager.authenticate(authentication);
      SecurityContextHolder.getContext().setAuthentication(authentication);
      if (!authentication.isAuthenticated()) {
        LOGGER.debug("computeValidToken({}, [PROTECTED]) : Authentication failed!!!", principal);
        return null;
      }
      if (authentication.getPrincipal() != null) {
        LOGGER.debug("computeValidToken({}, [PROTECTED]) - passed", principal);
        UserDetails userContext = (UserDetails) authentication.getPrincipal();
        String newToken = tokenManager.createNewToken(userContext);
        /* une fois l'authentification réalisée, on occulte le mot de passe */
        ((RestUserSecurityContext) userContext).setPassword("[PROTECTED]");
        if (newToken == null) {
          LOGGER.debug("creation token failed");
          return null;
        }
        return newToken;
      }
    } catch (AuthenticationException e) {
      LOGGER.debug("computeValidToken("+  principal +", [PROTECTED]) failed!!!", e);
    }
    return null;
  }

  @Override
  public String computeValidSsoToken(String principal, String motDePasse) {

    LOGGER.debug("computeValidSsoToken({}, [PROTECTED])", principal);
    Authentication authentication = new UsernamePasswordAuthenticationToken(principal, motDePasse);
    try {
      authentication = authenticationManager.authenticate(authentication);
      SecurityContextHolder.getContext().setAuthentication(authentication);
      if (!authentication.isAuthenticated()) {
        LOGGER.debug("computeValidSsoToken({}, [PROTECTED]) : Authentication failed!!!", principal);
        return null;
      }
      if (authentication.getPrincipal() != null) {
        LOGGER.debug("computeValidSsoToken({}, [PROTECTED]) - passed", principal);
        UserDetails userContext = (UserDetails) authentication.getPrincipal();
        String newToken = tokenManager.createNewSsoToken(userContext);
        /* une fois l'authentification réalisée, on occulte le mot de passe */
        ((RestUserSecurityContext) userContext).setPassword("[PROTECTED]");
        if (newToken == null) {
          LOGGER.debug("creation token SSO failed");
          return null;
        }
        return newToken;
      }
    } catch (AuthenticationException e) {
    	LOGGER.debug("computeValidSsoToken("+  principal +", [PROTECTED]) failed!!!", e);
    }
    return null;
  }

  @Override
  public UserDetails authenticate(String principal, String motDePasse) {

    LOGGER.debug("authenticate({}, [PROTECTED])", principal);
    Authentication authentication = new UsernamePasswordAuthenticationToken(principal, motDePasse);
    try {
      authentication = authenticationManager.authenticate(authentication);
      SecurityContextHolder.getContext().setAuthentication(authentication);
      if (!authentication.isAuthenticated()) {
        return null;
      }
      if (authentication.getPrincipal() != null) {
        LOGGER.debug("authenticate({}, [PROTECTED]) - passed", principal);
        UserDetails userContext = (UserDetails) authentication.getPrincipal();
        /* une fois l'authentification réalisée, on occulte le mot de passe */
        ((RestUserSecurityContext) userContext).setPassword("[PROTECTED]");
        return userContext;
      }
    } catch (AuthenticationException e) {
    	LOGGER.debug("computeValidToken("+  principal +", [PROTECTED]) failed!!!", e);
    }
    return null;
  }

  @Override
  public boolean checkToken(String token) {

    LOGGER.debug("checkToken : {}", token);
    UserDetails userDetails = tokenManager.getUserDetails(token);
    if (userDetails == null) {
      User user;
      StringTokenizer tokenizer = new StringTokenizer(token, "#");
      if (tokenizer.countTokens() != 2) {
        return tokenManager.reactivateToken(token, this) != null;
      }
      String firstPart = tokenizer.nextToken();
      String secondPart = tokenizer.nextToken();
      if (firstPart.equals(TOKEN_RECO_PASSIVE_KEYWORD)) {
        // secondPart est le numero de dossier
        user = userDao.getByPrincipalReconnaissancePassive(secondPart);
        if (user == null) {
          return false;
        }
        // Afin que les hashcodes d'un meme utilisateur soient différents en
        // actif et passif :
        user.setNom(TOKEN_RECO_PASSIVE_KEYWORD);
        userDetails = new RestUserSecurityContext(user);
        tokenManager.insertToken(token, userDetails);
      } else if (firstPart.equals(TOKEN_GENERIQUE_KEYWORD)) {
        // secondPart est le numero de dossier du visiteur
        // point n'est besoin de verifier le mot de passe pour ce mode d'accès
        // il faut donc bien limiter ce mécanisme aux numeros de dossier
        // génériques autorisés (USERS_GEN_SANS_AUTHENT) :
        if (Arrays.asList(USERS_GEN_SANS_AUTHENT).contains(secondPart)) {
          user = userDao.getByPrincipal(secondPart);
          if (user == null) {
            LOGGER.debug("utilisateur {} not found", secondPart);
            return false;
          }
          userDetails = new RestUserSecurityContext(user);
          tokenManager.insertToken(token, userDetails);
        } else {
          LOGGER.debug("utilisateur {} non autorisé à accéder à l'API sans authentification", secondPart);
          return false;
        }
      } else {
        LOGGER.debug("pattern de token non prévu");
        return false;
      }
    }
    UsernamePasswordAuthenticationToken securityToken =
        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(securityToken);
    return true;
  }

  @Override
  public boolean checkSsoToken(String token) {

    LOGGER.debug("checkSsoToken : {}", token);
    UserDetails userDetails = tokenManager.getUserDetails(token);
    if (userDetails == null) {
      return tokenManager.reactivateSsoToken(token, this) != null;
    }
    UsernamePasswordAuthenticationToken securityToken =
        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(securityToken);
    return true;
  }

}
