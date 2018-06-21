package fr.demo.metier.service.security.authentication;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

public final class TokenInfo {

  private String singleToken;

  private List<String> tokens;

  private final UserDetails userDetails;

  public TokenInfo(String token, UserDetails userDetails) {
    singleToken = token;
    this.userDetails = userDetails;
  }

  public List<String> getTokens() {
    if (singleToken == null) {
      return tokens;
    } else {
      List<String> result = new ArrayList<>();
      result.add(singleToken);
      return result;
    }
  }

  public void addToken(String token) {
    if (singleToken == null) {
      tokens.add(token);
    } else {
      tokens = new ArrayList<>();
      tokens.add(singleToken);
      tokens.add(token);
      singleToken = null;
    }
  }

  public UserDetails getUserDetails() {
    return userDetails;
  }

  @Override
  public String toString() {
    return "TokenInfo : tokens=" + tokens + ", userDetails" + userDetails;
  }

}
