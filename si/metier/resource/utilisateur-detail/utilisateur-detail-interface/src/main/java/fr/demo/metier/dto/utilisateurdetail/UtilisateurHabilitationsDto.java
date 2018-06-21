package fr.demo.metier.dto.utilisateurdetail;

import java.util.BitSet;

/**
 * UtilisateurDto utilisé pour la sécurisation de l'API RESTful
 */
public class UtilisateurHabilitationsDto extends UtilisateurDetailDto {

  protected Long idTechnique;

  protected BitSet habilitations;

  public UtilisateurHabilitationsDto() {
  }

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
