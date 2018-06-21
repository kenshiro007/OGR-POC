package fr.demo.metier.service.security.domain;


public class SecurityContext {

  User user;

  public SecurityContext(User user) {
    this.user = user;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

}
