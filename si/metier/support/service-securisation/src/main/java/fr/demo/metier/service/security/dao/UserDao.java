package fr.demo.metier.service.security.dao;

import fr.demo.metier.dto.utilisateurdetail.UtilisateurHabilitationsDto;
import fr.demo.metier.service.security.domain.User;
import fr.demo.metier.service.utilisateurdetail.UtilisateurDetailService;
import fr.demo.metier.converter.security.UtilisateurHabilitationsDtoTransformer;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("userDao")
public class UserDao {

  @Resource(name = "utilisateurDetailService")
  private UtilisateurDetailService utilisateurDetailService;

  @Resource(name = "securisation.utilisateurHabilitationsTransformer")
  private UtilisateurHabilitationsDtoTransformer utilisateurHabilitationsDtoTransformer;

  /**
   * Retourne un User avec ses habilitations.
   * Cas particulier pour les (Cadre/Jd/Interlocuteurs) qui peuvent avoir
   * à un instant un profil 'RECO ACTIVE' xor 'RECO PASSIVE'.
   *
   * @param numeroDossier
   * @return
   */
  public User getByPrincipal(String numeroDossier) {

    UtilisateurHabilitationsDto utilisateurHabilitationsDto =
        utilisateurDetailService.getDetailUtilisateurHabilitations(numeroDossier);
    return utilisateurHabilitationsDtoTransformer.transform(utilisateurHabilitationsDto);
  }

  /**
   * Retourne un User Personnel avec ses habilitations.
   *
   * @param activeDirectoryLogin
   * @return
   */
  public User getByActiveDirectoryLogin(String activeDirectoryLogin) {

    UtilisateurHabilitationsDto utilisateurHabilitationsDto =
        utilisateurDetailService.getDetailUtilisateurHabilitationsByActiveDirectoryLogin(activeDirectoryLogin);
    return utilisateurHabilitationsDtoTransformer.transform(utilisateurHabilitationsDto);
  }

  /**
   * Retourne un User avec ses habilitations (excluant les habilitations
   * pointées par des profils contenant 'RECO-ACTIVE'). Appelé quand
   * l'authentification est passive.
   *
   * @param numeroDossier
   * @return
   */
  public User getByPrincipalReconnaissancePassive(String numeroDossier) {

    UtilisateurHabilitationsDto utilisateurHabilitationsDto =
        utilisateurDetailService.getDetailUtilisateurHabilitationsRecoPassive(numeroDossier);
    return utilisateurHabilitationsDtoTransformer.transform(utilisateurHabilitationsDto);
  }

}
