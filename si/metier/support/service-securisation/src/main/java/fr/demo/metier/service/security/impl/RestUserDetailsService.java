package fr.demo.metier.service.security.impl;

import fr.demo.metier.service.security.dao.UserDao;
import fr.demo.metier.service.security.domain.User;
import fr.demo.metier.service.security.utils.SsoAuthenticationUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;

public class RestUserDetailsService implements UserDetailsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(RestUserDetailsService.class);

  @Resource(name = "userDao")
  private UserDao userDao;

  @Resource(name = "ssoAuthenticationUtils")
  private SsoAuthenticationUtils ssoAuthenticationUtils;

  @Override
  public UserDetails loadUserByUsername(String principal) throws UsernameNotFoundException {

    User user;
    LOGGER.debug("loadUserByUsername({})", principal);
    if (ssoAuthenticationUtils.isSsoAuthentication(principal)) {
      user = userDao.getByActiveDirectoryLogin(ssoAuthenticationUtils.getActiveDirectoryLoginFromSsoAuthentication(principal));
    } else {
      user = userDao.getByPrincipal(principal);
    }
    if (user == null) {
      LOGGER.debug("User not found({})", principal);
      throw new UsernameNotFoundException("User(" + principal + ") not found");
    }
    return new RestUserSecurityContext(user);
  }

}
