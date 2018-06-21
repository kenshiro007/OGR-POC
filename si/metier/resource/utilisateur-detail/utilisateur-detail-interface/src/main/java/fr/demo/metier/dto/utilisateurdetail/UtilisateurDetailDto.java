package fr.demo.metier.dto.utilisateurdetail;

/**
 * UtilisateurDto utilisé pour la sécurisation de l'API APEC RESTful
 */
public class UtilisateurDetailDto {

  protected String numeroDossier;

  protected String prenom;

  protected String nom;

  protected String typeUser;

  public UtilisateurDetailDto() {
  }

  public String getNumeroDossier() {
    return numeroDossier;
  }

  public void setNumeroDossier(String numeroDossier) {
    this.numeroDossier = numeroDossier;
  }

  public String getPrenom() {
    return prenom;
  }

  public void setPrenom(String prenom) {
    this.prenom = prenom;
  }

  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public String getTypeUserApec() {
    return typeUser;
  }

  public void setTypeUserApec(String typeUser) {
    this.typeUser = typeUser;
  }

}
