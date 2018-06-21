package fr.demo.metier.service.security.impl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import fr.demo.metier.service.security.domain.SecurityContext;
import fr.demo.metier.service.security.domain.User;

import java.util.Collection;
import java.util.Objects;

public class RestUserSecurityContext implements UserDetails {

  protected SecurityContext securityContext;

  public RestUserSecurityContext(User user) {
    this.securityContext = new SecurityContext(user);
  }

  public RestUserSecurityContext(SecurityContext securityContext) {
    this.securityContext = securityContext;
  }

  public SecurityContext getSecurityContext() {
    return securityContext;
  }

  public void setSecurityContext(SecurityContext securityContext) {
    this.securityContext = securityContext;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  public User getUser() {
    return securityContext.getUser();
  }

  @Override
  public String getPassword() {
    return securityContext.getUser().getMotDePasse();
  }

  public void setPassword(String motDePasse) {
    securityContext.getUser().setMotDePasse(motDePasse);
  }

  @Override
  public String getUsername() {
    return securityContext.getUser().getPrincipal();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    return this == o
        || o != null
        && o instanceof RestUserSecurityContext
        && Objects.equals(securityContext.getUser(), ((RestUserSecurityContext) o).securityContext.getUser());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getUser());
  }

  @Override
  public String toString() {
    return "RestUserSecurityContext(user=" + securityContext.getUser() + ")";
  }

}
