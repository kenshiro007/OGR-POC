package fr.demo.metier.service.security.impl;

import fr.demo.metier.service.utilisateurdetail.UtilisateurDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.expression.SecurityExpressionOperations;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebSecurityExpressionRoot;

import javax.annotation.Resource;


public class RestWebSecurityExpressionHandler extends DefaultWebSecurityExpressionHandler {

  @Autowired
  AuthenticationTrustResolver authenticationTrustResolver;

  @Resource(name = "utilisateurDetailService")
  UtilisateurDetailService utilisateurDetailService;

  protected SecurityExpressionOperations createSecurityExpressionRoot(Authentication authentication, FilterInvocation fi) {
    WebSecurityExpressionRoot restWebSecurityExpressionRoot =
        new RestWebSecurityExpressionRoot(authentication, fi, utilisateurDetailService);
    restWebSecurityExpressionRoot.setPermissionEvaluator(this.getPermissionEvaluator());
    restWebSecurityExpressionRoot.setTrustResolver(authenticationTrustResolver);
    restWebSecurityExpressionRoot.setRoleHierarchy(this.getRoleHierarchy());
    return restWebSecurityExpressionRoot;
  }

}
