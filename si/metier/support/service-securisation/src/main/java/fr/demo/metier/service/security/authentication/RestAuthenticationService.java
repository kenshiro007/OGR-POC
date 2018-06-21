package fr.demo.metier.service.security.authentication;

import org.springframework.security.core.userdetails.UserDetails;

public interface RestAuthenticationService {

  String computeValidToken(String principal, String motDePasse);

  String computeValidSsoToken(String principal, String motDePasse);

  UserDetails authenticate(String principal, String motDePasse);

  boolean checkToken(String token);

  boolean checkSsoToken(String token);

}
