package fr.demo.metier.service.utilisateurdetail.impl;

import fr.demo.metier.converter.utilisateurdetail.UtilisateurDetailTransformer;
import fr.demo.metier.converter.utilisateurdetail.UtilisateurHabilitationsTransformer;
import fr.demo.metier.dao.utilisateurdetail.impl.UtilisateurDetailDao;
import fr.demo.metier.dao.utilisateurdetail.impl.UtilisateurHabilitationsDao;
import fr.demo.metier.domain.utilisateurdetail.UtilisateurDetail;
import fr.demo.metier.domain.utilisateurdetail.UtilisateurHabilitations;
import fr.demo.metier.dto.utilisateurdetail.UtilisateurDetailDto;
import fr.demo.metier.dto.utilisateurdetail.UtilisateurHabilitationsDto;
import fr.demo.metier.service.utilisateurdetail.UtilisateurDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

@Component("utilisateurDetailService")
@Transactional(propagation = Propagation.REQUIRED)
public class UtilisateurDetailServiceImpl implements UtilisateurDetailService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UtilisateurDetailServiceImpl.class);

  @Resource(name = "utilisateurdetail.utilisateurHabilitationsTransformer")
  UtilisateurHabilitationsTransformer utilisateurHabilitationsTransformer;

  @Resource(name = "utilisateurdetail.utilisateurHabilitationsDao")
  private UtilisateurHabilitationsDao utilisateurHabilitationsDao;

  @Resource(name = "utilisateurdetail.utilisateurDetailTransformer")
  UtilisateurDetailTransformer utilisateurDetailTransformer;

  @Resource(name = "utilisateurdetail.utilisateurDetailDao")
  private UtilisateurDetailDao utilisateurDetailDao;


  @Transactional(readOnly = true)
  UtilisateurHabilitations getUtilisateurHabilitations(String numeroDossier) {
    LOGGER.debug("getUtilisateurHabilitations({})", numeroDossier);
    return utilisateurHabilitationsDao.getUtilisateur(numeroDossier);
  }

  @Transactional(readOnly = true)
  UtilisateurHabilitations getUtilisateurHabilitationsByActiveDirectoryLogin(String activeDirectoryLogin) {
    LOGGER.debug("getUtilisateurHabilitationsByActiveDirectoryLogin({})", activeDirectoryLogin);
    return utilisateurHabilitationsDao.getUtilisateurByActiveDirectoryLogin(activeDirectoryLogin);
  }

  @Transactional(readOnly = true)
  UtilisateurHabilitations getUtilisateurHabilitationsRecoPassive(String numeroDossier) {
    LOGGER.debug("getUtilisateurHabilitations({})", numeroDossier);
    return utilisateurHabilitationsDao.getUtilisateurRecoPassive(numeroDossier);
  }


  @Transactional(readOnly = true)
  List<UtilisateurDetail> getUtilisateurs(List<String> listeNumerosDossier) {
    LOGGER.debug("getUtilisateurs");
    return utilisateurDetailDao.getUtilisateurs(listeNumerosDossier);
  }

  @Transactional(readOnly = true)
  BitSet getCodeHabilitations(String listeHabilitations) {
    LOGGER.debug("getCodeHabilitationsPourHabilitations");
    return utilisateurHabilitationsDao.getCodeHabilitationsPourHabilitations(listeHabilitations);
  }

  @Override
  public UtilisateurHabilitationsDto getDetailUtilisateurHabilitations(String numeroDossier) {

    UtilisateurHabilitations utilisateurHabilitations = getUtilisateurHabilitations(numeroDossier);
    return utilisateurHabilitationsTransformer.transform(utilisateurHabilitations);
  }

  @Override
  public UtilisateurHabilitationsDto getDetailUtilisateurHabilitationsByActiveDirectoryLogin(String activeDirectoryLogin) {

    UtilisateurHabilitations utilisateurHabilitations = getUtilisateurHabilitationsByActiveDirectoryLogin(activeDirectoryLogin);
    return utilisateurHabilitationsTransformer.transform(utilisateurHabilitations);
  }

  @Override
  public UtilisateurHabilitationsDto getDetailUtilisateurHabilitationsRecoPassive(String numeroDossier) {

    UtilisateurHabilitations utilisateurHabilitations = getUtilisateurHabilitationsRecoPassive(numeroDossier);
    return utilisateurHabilitationsTransformer.transform(utilisateurHabilitations);
  }

  @Override
  public List<UtilisateurDetailDto> getDetailUtilisateurs(List<String> listeNumerosDossier) {

    List<UtilisateurDetail> utilisateurDetails = getUtilisateurs(listeNumerosDossier);
    List<UtilisateurDetailDto> result = new ArrayList<>();
    for (UtilisateurDetail item : utilisateurDetails) {
      result.add(utilisateurDetailTransformer.transform(item));
    }
    return result;
  }

  @Override
  public BitSet getCodeHabilitationsPourHabilitations(String listeHabilitations) {
    return getCodeHabilitations(listeHabilitations);
  }

}