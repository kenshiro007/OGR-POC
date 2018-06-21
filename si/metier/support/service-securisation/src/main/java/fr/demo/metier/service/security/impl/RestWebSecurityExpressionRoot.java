package fr.demo.metier.service.security.impl;

import fr.demo.metier.service.utilisateurdetail.UtilisateurDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.WebSecurityExpressionRoot;

import java.util.BitSet;


public class RestWebSecurityExpressionRoot extends WebSecurityExpressionRoot {

  private static final Logger LOGGER = LoggerFactory.getLogger(RestWebSecurityExpressionRoot.class);

  UtilisateurDetailService utilisateurDetailService;

  public RestWebSecurityExpressionRoot(Authentication authentication, FilterInvocation filterInvocation) {

    super(authentication, filterInvocation);
  }

  public RestWebSecurityExpressionRoot(
      Authentication authentication, FilterInvocation filterInvocation,
      UtilisateurDetailService utilisateurDetailService) {

    super(authentication, filterInvocation);
    this.utilisateurDetailService = utilisateurDetailService;
  }

  public boolean means(final String habilitations) {
    LOGGER.debug("means(...) pour {}", getPrincipal());
    BitSet habilitationsUtilisateur =
        ((RestUserSecurityContext) getAuthentication().getPrincipal()).getSecurityContext().getUser().getHabilitations();
    BitSet habilitationsBitSet = utilisateurDetailService.getCodeHabilitationsPourHabilitations(habilitations);
    habilitationsBitSet.and(habilitationsUtilisateur);
    return !habilitationsBitSet.isEmpty();
  }

}
