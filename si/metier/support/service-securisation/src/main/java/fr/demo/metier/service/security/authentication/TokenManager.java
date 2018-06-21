package fr.demo.metier.service.security.authentication;

import org.springframework.security.core.userdetails.UserDetails;

public interface TokenManager {

  void resetRestTokenManager();

  UserDetails getUserDetails(String token);

  String createNewToken(UserDetails userDetails);
  
  String createNewSsoToken(UserDetails userDetails);

  String reactivateToken(String token, RestAuthenticationService authenticationService);
  
  String reactivateSsoToken(String token, RestAuthenticationService authenticationService);

  void insertToken(String token, UserDetails userDetails);

  Integer getNumberOfUsers();

  Integer getNumberOfToken();

  Long getUsersCapacity();

  void setUsersCapacity(Long capacity);

}
