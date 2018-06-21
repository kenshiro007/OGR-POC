package fr.demo.metier.dao.utilisateurdetail;

import org.apache.ibatis.annotations.Param;

import fr.demo.metier.dao.core.impl.GenericMapper;
import fr.demo.metier.domain.utilisateurdetail.UtilisateurHabilitations;

public interface UtilisateurHabilitationsMapper extends GenericMapper<String, UtilisateurHabilitations> {

  /**
   * Retourne un utilisateur (personnel ou générique) avec ses codes habilitations

   * @param numeroDossier
   * @return
   */
  UtilisateurHabilitations getUtilisateurPersonnelApecOuGenerique(@Param("numeroDossier") String numeroDossier);

  /**
   * Retourne un utilisateur (personnel) avec ses codes habilitations
   *
   * @param activeDirectoryLogin
   * @return
   */
  UtilisateurHabilitations getUtilisateurPersonnelApecByActiveDirectoryLogin(@Param("activeDirectoryLogin") String activeDirectoryLogin);

  /**
   * Retourne un utilisateur (cadre, jd, interlocuteur) avec ses habilitations
   * vides (à remplir par ailleurs)
   *
   * @param numeroDossier
   * @return
   */
  UtilisateurHabilitations getUtilisateurClient(@Param("numeroDossier") String numeroDossier);

  /**
   * Retourne l'organisation (nomenclature) correspondant à un utilisateur
   *
   * @param numeroDossier
   * @return
   */
  Long getUserOrganisation(@Param("numeroDossier") String numeroDossier);

  /**
   * Retourne les codes habilitations pour un utilisateur
   *
   * @param numeroDossier
   * @return
   */
  String getCodeHabilisationsPourPersonnelApecOuGenerique(@Param("numeroDossier") String numeroDossier);

  /**
   * Retourne les codes habilitations pour un client (via code organisation avec
   * exclusions)
   *
   * @param userOrganisation
   * @param exclusions
   * @return
   */
  String getCodeHabilitationsPourClient(@Param("userOrganisation") Long userOrganisation,
                                        @Param("exclusions") String exclusions);

  /**
   * Retourne les codes habilitations pour une liste d'habilitations
   *
   * @param listeHabilitations
   * @return
   */
  String getCodeHabilitationsPourHabilitations(@Param("listeHabilitations") String listeHabilitations);

}
