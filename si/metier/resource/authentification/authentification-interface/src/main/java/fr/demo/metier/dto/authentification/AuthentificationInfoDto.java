package fr.demo.metier.dto.authentification;

import java.io.Serializable;
import java.util.Date;

public class AuthentificationInfoDto implements Serializable {

  private static final long serialVersionUID = 1L;

  protected Long idCompte;

  protected Boolean cguAcceptees;

  protected Date dateExpiration;

  protected Date dateModificationPassword;

  public Long getId() {
    return idCompte;
  }

  public void setId(Long idCompte) {
    this.idCompte = idCompte;
  }

  public Boolean getCguAcceptees() {
    return cguAcceptees;
  }

  public void setCguAcceptees(Boolean cguAcceptees) {
    this.cguAcceptees = cguAcceptees;
  }

  public Date getDateExpiration() {
    return dateExpiration;
  }

  public void setDateExpiration(Date dateExpiration) {
    this.dateExpiration = dateExpiration;
  }

  public Date getDateModificationPassword() {
    return dateModificationPassword;
  }

  public void setDateModificationPassword(Date dateModificationPassword) {
    this.dateModificationPassword = dateModificationPassword;
  }
}

