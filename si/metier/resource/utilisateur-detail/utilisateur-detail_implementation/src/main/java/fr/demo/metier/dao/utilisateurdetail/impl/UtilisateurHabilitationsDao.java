package fr.demo.metier.dao.utilisateurdetail.impl;

import fr.demo.metier.converter.utilisateurdetail.HabilitationsBitSetTransformer;
import fr.demo.metier.dao.core.impl.MyBatisGenericMapperDao;
import fr.demo.metier.dao.utilisateurdetail.UtilisateurHabilitationsMapper;
import fr.demo.metier.domain.utilisateurdetail.UtilisateurHabilitations;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.BitSet;

@Repository("utilisateurdetail.utilisateurHabilitationsDao")
public class UtilisateurHabilitationsDao
    extends
    MyBatisGenericMapperDao<String, UtilisateurHabilitations, UtilisateurHabilitationsMapper> {

  private static final Logger LOGGER = LoggerFactory.getLogger(UtilisateurHabilitationsDao.class);

  @Resource(name = "utilisateurdetail.habilitationsBitSetTransformer")
  private HabilitationsBitSetTransformer habilitationsBitSetTransformer;

  private static final String KEYWORD_BDD_RECO_ACTIVE = "RECO_ACTIVE";

  private static final String KEYWORD_BDD_RECO_PASSIVE = "RECO_PASSIVE";

  private static final Long ORGANISATION_CADRE = 200183L;

  private static final Long ORGANISATION_JD = 200859L;

  private static final Long ORGANISATION_INTERLOCUTEUR = 200184L;

  private BitSet habilitationsCadreReconnaissancePassive;

  private BitSet habilitationsCadreReconnaissanceActive;

  private BitSet habilitationsJdReconnaissancePassive;

  private BitSet habilitationsJdReconnaissanceActive;

  private BitSet habilitationsInterlocuteurReconnaissancePassive;

  private BitSet habilitationsInterlocuteurReconnaissanceActive;

  public UtilisateurHabilitationsDao() {
    super(UtilisateurHabilitationsMapper.class);
  }

  public UtilisateurHabilitations getUtilisateur(String numeroDossier) {
    SqlSession session = getSqlSession();
    UtilisateurHabilitations result;
    Long userOrganisation = session.getMapper(getBasicMapper()).getUserOrganisation(numeroDossier);
    LOGGER.debug("getUtilisateur ----> User : {} Organisation : {}", numeroDossier, userOrganisation);
    if (userOrganisation == null) {
      return null;
    }
    if (userOrganisation.equals(ORGANISATION_CADRE)) {
      result = session.getMapper(getBasicMapper()).getUtilisateurClient(numeroDossier);
      result.setHabilitations(getHabilitationsCadreReconnaissanceActive());
    } else if (userOrganisation.equals(ORGANISATION_JD)) {
      result = session.getMapper(getBasicMapper()).getUtilisateurClient(numeroDossier);
      result.setHabilitations(getHabilitationsJdReconnaissanceActive());
    } else if (userOrganisation.equals(ORGANISATION_INTERLOCUTEUR)) {
      result = session.getMapper(getBasicMapper()).getUtilisateurClient(numeroDossier);
      result.setHabilitations(getHabilitationsInterlocuteurReconnaissanceActive());
    } else {
      result = session.getMapper(getBasicMapper()).getUtilisateurPersonnelApecOuGenerique(numeroDossier);
      if (result.getHabilitations() == null) {
        String habilitations = session.getMapper(getBasicMapper()).getCodeHabilisationsPourPersonnelApecOuGenerique(numeroDossier);
        result.setHabilitations(habilitationsBitSetTransformer.transform(habilitations));
      }
    }
    return result;
  }

  public UtilisateurHabilitations getUtilisateurByActiveDirectoryLogin(String activeDirectoryLogin) {
    SqlSession session = getSqlSession();
    UtilisateurHabilitations result;
    result = session.getMapper(getBasicMapper()).getUtilisateurPersonnelApecByActiveDirectoryLogin(activeDirectoryLogin);
    if (result.getHabilitations() == null) {
      String habilitations = session.getMapper(getBasicMapper()).getCodeHabilisationsPourPersonnelApecOuGenerique(result.getId());
      result.setHabilitations(habilitationsBitSetTransformer.transform(habilitations));
    }
    return result;
  }

  public UtilisateurHabilitations getUtilisateurRecoPassive(String numeroDossier) {
    SqlSession session = getSqlSession();
    UtilisateurHabilitations result;
    Long userOrganisation = session.getMapper(getBasicMapper()).getUserOrganisation(numeroDossier);
    LOGGER.debug("getUtilisateurRecoPassive ----> User : {} Organisation : {}", numeroDossier, userOrganisation);
    if (userOrganisation == null) {
      return null;
    }
    if (userOrganisation.equals(ORGANISATION_CADRE)) {
      result = session.getMapper(getBasicMapper()).getUtilisateurClient(numeroDossier);
      result.setHabilitations(getHabilitationsCadreReconnaissancePassive());
    } else if (userOrganisation.equals(ORGANISATION_JD)) {
      result = session.getMapper(getBasicMapper()).getUtilisateurClient(numeroDossier);
      result.setHabilitations(getHabilitationsJdReconnaissancePassive());
    } else if (userOrganisation.equals(ORGANISATION_INTERLOCUTEUR)) {
      result = session.getMapper(getBasicMapper()).getUtilisateurClient(numeroDossier);
      result.setHabilitations(getHabilitationsInterlocuteurReconnaissancePassive());
    } else {
      LOGGER.debug("Type utilisateur incompatible avec reco passive ----> User : {}", numeroDossier);
      return null;
    }
    return result;
  }

  /**
   * Traduit une ligne de configuration du fichier spring security en BitSet poour valider les droits
   * @param listeHabilitations
   * @return
   */
  public BitSet getCodeHabilitationsPourHabilitations(String listeHabilitations) {
    SqlSession session = getSqlSession();
    String habilitations =
        session.getMapper(getBasicMapper()).getCodeHabilitationsPourHabilitations(listeHabilitations);
    return habilitationsBitSetTransformer.transform(habilitations);
  }

  public BitSet getHabilitationsCadreReconnaissancePassive() {
    SqlSession session = getSqlSession();
    if (habilitationsCadreReconnaissancePassive == null) {
      String habilitations =
          session.getMapper(getBasicMapper()).getCodeHabilitationsPourClient(
              ORGANISATION_CADRE,
              KEYWORD_BDD_RECO_ACTIVE);
      habilitationsCadreReconnaissancePassive = habilitationsBitSetTransformer.transform(habilitations);
    }
    return habilitationsCadreReconnaissancePassive;
  }

  public BitSet getHabilitationsCadreReconnaissanceActive() {
    SqlSession session = getSqlSession();
    if (habilitationsCadreReconnaissanceActive == null) {
      String habilitations =
          session.getMapper(getBasicMapper()).getCodeHabilitationsPourClient(
              ORGANISATION_CADRE,
              KEYWORD_BDD_RECO_PASSIVE);
      habilitationsCadreReconnaissanceActive = habilitationsBitSetTransformer.transform(habilitations);
    }
    return habilitationsCadreReconnaissanceActive;
  }

  public BitSet getHabilitationsJdReconnaissancePassive() {
    SqlSession session = getSqlSession();
    if (habilitationsJdReconnaissancePassive == null) {
      String habilitations =
          session.getMapper(getBasicMapper()).getCodeHabilitationsPourClient(ORGANISATION_JD, KEYWORD_BDD_RECO_ACTIVE);
      habilitationsJdReconnaissancePassive = habilitationsBitSetTransformer.transform(habilitations);
    }
    return habilitationsJdReconnaissancePassive;
  }

  public BitSet getHabilitationsJdReconnaissanceActive() {
    SqlSession session = getSqlSession();
    if (habilitationsJdReconnaissanceActive == null) {
      String habilitations =
          session.getMapper(getBasicMapper()).getCodeHabilitationsPourClient(ORGANISATION_JD, KEYWORD_BDD_RECO_PASSIVE);
      habilitationsJdReconnaissanceActive = habilitationsBitSetTransformer.transform(habilitations);
    }
    return habilitationsJdReconnaissanceActive;
  }

  public BitSet getHabilitationsInterlocuteurReconnaissancePassive() {
    SqlSession session = getSqlSession();
    if (habilitationsInterlocuteurReconnaissancePassive == null) {
      String habilitations =
          session.getMapper(getBasicMapper()).getCodeHabilitationsPourClient(
              ORGANISATION_INTERLOCUTEUR,
              KEYWORD_BDD_RECO_ACTIVE);
      habilitationsInterlocuteurReconnaissancePassive = habilitationsBitSetTransformer.transform(habilitations);
    }
    return habilitationsInterlocuteurReconnaissancePassive;
  }

  public BitSet getHabilitationsInterlocuteurReconnaissanceActive() {
    SqlSession session = getSqlSession();
    if (habilitationsInterlocuteurReconnaissanceActive == null) {
      String habilitations =
          session.getMapper(getBasicMapper()).getCodeHabilitationsPourClient(
              ORGANISATION_INTERLOCUTEUR,
              KEYWORD_BDD_RECO_PASSIVE);
      habilitationsInterlocuteurReconnaissanceActive = habilitationsBitSetTransformer.transform(habilitations);
    }
    return habilitationsInterlocuteurReconnaissanceActive;
  }

}
