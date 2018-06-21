package fr.demo.metier.service.security.domain;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Objects;

public class User {


  public static final String KEYWORD_PERSONNEL = "PERSONNEL";
  public static final String TO_STRING_USER = "User : (";
  public static final String TO_STRING_PROTECTED = "/[PROTECTED]/";

  private String principal;

  private Long idTechnique;

  private String nom;

  private String motDePasse;

  private String typeUser;

  private BitSet habilitations;

  public User(String principal, Long idTechnique, String nom, String typeUser) {
    this.principal = principal;
    this.idTechnique = idTechnique;
    this.nom = nom;
    this.typeUser = typeUser;
  }

  public String getPrincipal() {
    return principal;
  }

  public void setPrincipal(String principal) {
    this.principal = principal;
  }

  public Long getIdTechnique() {
    return idTechnique;
  }

  public void setIdTechnique(Long idTechnique) {
    this.idTechnique = idTechnique;
  }

  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public String getMotDePasse() {
    return motDePasse;
  }

  public void setMotDePasse(String motDePasse) {
    this.motDePasse = motDePasse;
  }

  public String getTypeUser() {
    return typeUser;
  }

  public void setTypeUserA(String typeUser) {
    this.typeUser = typeUser;
  }

  public BitSet getHabilitations() {
    return habilitations;
  }

  public void setHabilitations(BitSet habilitations) {
    this.habilitations = habilitations;
  }


  public boolean isPersonnel() {
    return KEYWORD_PERSONNEL.equals(getTypeUser());
  }

  public boolean isUserGenerique() {
    return Arrays.asList(USERS_GENERIQUES).contains(getTypeUser());
  }

  @Override
  public boolean equals(Object o) {
    return this == o || o != null && o instanceof User && Objects.equals(principal + nom, ((User) o).getPrincipal() + ((User) o).getNom());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(principal + nom);
  }

  @Override
  public String toString() {
    return TO_STRING_USER + principal + TO_STRING_PROTECTED + nom + ", roles=" + habilitations + ")";
  }

}
