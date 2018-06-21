package fr.demo.metier.domain.utilisateurdetail;

import fr.demo.metier.model.core.GenericIdObject;

import java.io.Serializable;

/**
 * Utilisateur utilisé pour la sécurisation de l'API APEC RESTful
 */
public class UtilisateurDetail implements GenericIdObject<String>, Serializable {

  protected String numeroDossier;

  protected String nom;

  protected String prenom;

  protected String typeUser;

  @Override
  public String getId() {
    return numeroDossier;
  }

  @Override
  public void setId(String numeroDossier) {
    this.numeroDossier = numeroDossier;
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

  public String getTypeUser() {
    return typeUser;
  }

  public void setTypeUser(String typeUser) {
    this.typeUser = typeUser;
  }

}
