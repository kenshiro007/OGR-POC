package fr.demo.metier.model.core;

import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Date;

public class AuditObject implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -8780663808987479032L;

  private Date dateCreation = null;

  private Date dateModification = null;

  @Length(max = 30)
  private String utilisateurCreation = null;

  @Length(max = 30)
  private String utilisateurModification = null;

  public Date getDateCreation() {
    return dateCreation;
  }

  public void setDateCreation(Date dateCreation) {
    this.dateCreation = dateCreation;
  }

  public Date getDateModification() {
    return dateModification;
  }

  public void setDateModification(Date dateModification) {
    this.dateModification = dateModification;
  }

  public String getUtilisateurCreation() {
    return utilisateurCreation;
  }

  public void setUtilisateurCreation(String utilisateurCreation) {
    this.utilisateurCreation = utilisateurCreation;
  }

  public String getUtilisateurModification() {
    return utilisateurModification;
  }

  public void setUtilisateurModification(String utilisateurModification) {
    this.utilisateurModification = utilisateurModification;
  }
}
