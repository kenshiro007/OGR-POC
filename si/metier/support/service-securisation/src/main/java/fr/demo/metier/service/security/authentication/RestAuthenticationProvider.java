package fr.demo.metier.service.security.authentication;

import fr.demo.metier.dto.authentification.AuthentificationDto;
import fr.demo.metier.exception.ValidationException;
import fr.demo.metier.service.authentification.AuthentificationService;
import fr.demo.metier.service.security.domain.User;
import fr.demo.metier.service.security.impl.RestUserSecurityContext;
import fr.demo.metier.service.security.utils.SsoAuthenticationUtils;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.Assert;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
	
  private static final Logger LOGGER = LoggerFactory.getLogger(RestAuthenticationProvider.class);

  @Resource(name = "authentificationService")
  protected AuthentificationService authentificationService;

  @Resource(name = "ssoAuthenticationUtils")
  SsoAuthenticationUtils ssoAuthenticationUtils;

  protected UserDetailsService userDetailsService;

  public RestAuthenticationProvider() {
  }

  @Override
  protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
  }

  protected void doAfterPropertiesSet() throws Exception {

    Assert.notNull(this.userDetailsService, "A UserDetailsService must be set");
  }

  AuthentificationDto initAuthentification(UsernamePasswordAuthenticationToken authentication) {
    AuthentificationDto authentificationDto = new AuthentificationDto();
    authentificationDto.setNumeroCompte(authentication.getPrincipal().toString());
    authentificationDto.setMotDePasse(authentication.getCredentials().toString());
    return authentificationDto;
  }

  @Override
  protected final UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
      throws AuthenticationException {

    UserDetails loadedUser;
    try {
      // On commence par chercher le UserDetails (qui permet de connaitre le
      // type utilisateur : CJD/INTERLOCUTEUR/PERSONNEL) pour l'authentification
      loadedUser = this.getUserDetailsService().loadUserByUsername(username);
      if (loadedUser == null) {
        throw new InternalAuthenticationServiceException("UserDetailsService retourne null");
      }
      // Cas particulier d'une authentification requise par un tiers de
      // confiance sans le mot de passe :
      if (ssoAuthenticationUtils.isSsoAuthentication(username)) {
        String activeDirectoryLogin = ssoAuthenticationUtils.getActiveDirectoryLoginFromSsoAuthentication(username);
        if (ssoAuthenticationUtils.isPasswordOk(authentication.getCredentials().toString(), activeDirectoryLogin)) {
          return loadedUser;
        } else {
          throw new InternalAuthenticationServiceException("Authentification non réussie");
        }
      }
      // L'utilisateur existant, on doit valider l'authentification :
      AuthentificationDto authentificationDto = initAuthentification(authentication);
      boolean authentificationOK;
      User currentUser = ((RestUserSecurityContext) loadedUser).getUser();
      try {
	    
	      authentificationDto.setLogin(currentUser.getNom());
	      authentificationDto.setTypeAuthentifiant(AuthentificationDto.EnumTypeIdentifiant.LOGIN);
	      authentificationOK = authentificationService.getAuthentificationComptePersonnel(authentificationDto) != null;
    
      } catch (ValidationException e) {
    	LOGGER.error("UserDetailsService retourne null", e);
        throw new InternalAuthenticationServiceException("UserDetailsService retourne null");
      }
      if (authentificationOK) {
        // on positionne le mot de passe dans l'objet UserDetails afin de
        // s'intégrer dans le processus spring d'authentification :
        ((RestUserSecurityContext) loadedUser).setPassword(authentication.getCredentials().toString());
        return loadedUser;
      } else {
        throw new InternalAuthenticationServiceException("Authentification non réussie");
      }
    } catch (Exception ex) {
      LOGGER.error("Exception", ex);
      throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
    }
  }

  public void setUserDetailsService(UserDetailsService userDetailsService) {

    this.userDetailsService = userDetailsService;
  }

  protected UserDetailsService getUserDetailsService() {
    return this.userDetailsService;
  }

}
