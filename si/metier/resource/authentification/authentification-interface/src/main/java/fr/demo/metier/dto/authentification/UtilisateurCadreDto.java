package fr.demo.metier.dto.authentification;

import fr.demo.metier.model.core.IdObject;
import fr.demo.metier.model.core.InfoConnexions;
import fr.demo.metier.model.core.InfoConnexionsObject;

public class UtilisateurCadreDto extends UtilisateurGeneriqueDto implements IdObject, InfoConnexionsObject {

  private static final long serialVersionUID = 1L;

  private Long idCompte;

  private String email;

  private String nom;

  private String prenom;
  

  private InfoConnexions infoConnexions;

  private Long idProfilCadre;

  private Boolean cguAcceptees;

  private Boolean compteValide;

  public UtilisateurCadreDto() {
  }

  @Override
  public Long getId() {
    return idCompte;
  }

  @Override
  public void setId(Long idCompte) {
    this.idCompte = idCompte;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public String getPrenom() {
    return prenom;
  }

  public void setPrenom(String prenom) {
    this.prenom = prenom;
  }

  public Long getIdProfilCadre() {
    return idProfilCadre;
  }

  public void setIdProfilCadre(Long idProfilCadre) {
    this.idProfilCadre = idProfilCadre;
  }

  public Boolean getCguAcceptees() {
    return cguAcceptees;
  }

  public void setCguAcceptees(Boolean cguAcceptees) {
    this.cguAcceptees = cguAcceptees;
  }

  public Boolean getCompteValide() {
    return compteValide;
  }

  public void setCompteValide(Boolean compteValide) {
    this.compteValide = compteValide;
  }

  @Override
  public InfoConnexions getInfoConnexions() {
    return this.infoConnexions;
  }

  @Override
  public void setInfoConnexions(InfoConnexions infoConnexions) {
    this.infoConnexions = infoConnexions;
  }

}
