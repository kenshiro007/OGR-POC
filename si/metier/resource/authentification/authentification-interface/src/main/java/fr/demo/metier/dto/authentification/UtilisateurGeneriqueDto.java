package fr.demo.metier.dto.authentification;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UtilisateurGeneriqueDto implements Serializable {

  private static final long serialVersionUID = 1L;

  protected String numeroCompte;

  @JsonIgnore
  protected String motDePasse;

  @JsonIgnore
  protected String motDePasseSha512;

  @JsonIgnore
  protected String motDePasseSel;

  @JsonIgnore
  protected Date dateCreation;

  protected Date dateExpiration;

  public UtilisateurGeneriqueDto() {
  }

  public String getNumeroCompte() {
    return numeroCompte;
  }

  public void setNumeroCompte(String numeroCompte) {
    this.numeroCompte = numeroCompte;
  }

  public String getMotDePasse() {
    return motDePasse;
  }

  public void setMotDePasse(String motDePasse) {
    this.motDePasse = motDePasse;
  }

  public String getMotDePasseSha512() {
    return motDePasseSha512;
  }

  public void setMotDePasseSha512(String motDePasseSha512) {
    this.motDePasseSha512 = motDePasseSha512;
  }

  public String getMotDePasseSel() {
    return motDePasseSel;
  }

  public void setMotDePasseSel(String motDePasseSel) {
    this.motDePasseSel = motDePasseSel;
  }

  public Date getDateCreation() {
    return dateCreation;
  }

  public void setDateCreation(Date dateCreation) {
    this.dateCreation = dateCreation;
  }

  public Date getDateExpiration() {
    return dateExpiration;
  }

  public void setDateExpiration(Date dateExpiration) {
    this.dateExpiration = dateExpiration;
  }
}
