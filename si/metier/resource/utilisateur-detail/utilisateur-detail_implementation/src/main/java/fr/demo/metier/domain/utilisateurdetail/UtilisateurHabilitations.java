package fr.demo.metier.domain.utilisateurdetail;

import java.util.BitSet;

/**
 * UtilisateurHabilitations utilisé pour la sécurisation de l'API APEC RESTful
 */
public class UtilisateurHabilitations extends UtilisateurDetail {

  private static final long serialVersionUID = 1L;

  protected Long idTechnique;

  protected BitSet habilitations;

  public Long getIdTechnique() {
    return idTechnique;
  }

  public void setIdTechnique(Long idTechnique) {
    this.idTechnique = idTechnique;
  }

  public BitSet getHabilitations() {
    return habilitations;
  }

  public void setHabilitations(BitSet habilitations) {
    this.habilitations = habilitations;
  }

}
