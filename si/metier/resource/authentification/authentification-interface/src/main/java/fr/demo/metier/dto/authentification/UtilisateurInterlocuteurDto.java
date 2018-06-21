package fr.demo.metier.dto.authentification;

import fr.demo.metier.model.core.IdObject;
import fr.demo.metier.model.core.InfoConnexions;
import fr.demo.metier.model.core.InfoConnexionsObject;

public class UtilisateurInterlocuteurDto extends UtilisateurGeneriqueDto implements IdObject, InfoConnexionsObject {

  private static final long serialVersionUID = 1L;

  protected Long idCompte;

  private String email;

  private String nom;

  private String prenom;
  
  private Long idEtablissement;

  private String nomEtablissement;

  private InfoConnexions infosConnexions;

  private Boolean cguAcceptees;

  private Long idNomStatut;

  public UtilisateurInterlocuteurDto() {
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

  public Long getIdEtablissement() {
    return idEtablissement;
  }

  public void setIdEtablissement(Long idEtablissement) {
    this.idEtablissement = idEtablissement;
  }

  public String getNomEtablissement() {
    return nomEtablissement;
  }

  public void setNomEtablissement(String nomEtablissement) {
    this.nomEtablissement = nomEtablissement;
  }

  @Override
  public InfoConnexions getInfoConnexions() {
    return infosConnexions;
  }

  @Override
  public void setInfoConnexions(InfoConnexions infoConnexions) {
    this.infosConnexions = infoConnexions;
  }

  public Boolean getCguAcceptees() {
    return cguAcceptees;
  }

  public void setCguAcceptees(Boolean cguAcceptees) {
    this.cguAcceptees = cguAcceptees;
  }

  public Long getIdNomStatut() {
    return idNomStatut;
  }

  public void setIdNomStatut(Long idNomStatut) {
    this.idNomStatut = idNomStatut;
  }
}
