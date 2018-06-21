package fr.demo.metier.service.utilisateurdetail;

import fr.demo.metier.dto.utilisateurdetail.UtilisateurDetailDto;
import fr.demo.metier.dto.utilisateurdetail.UtilisateurHabilitationsDto;

import java.util.BitSet;
import java.util.List;

public interface UtilisateurDetailService {

  UtilisateurHabilitationsDto getDetailUtilisateurHabilitations(String numeroDossier);

  UtilisateurHabilitationsDto getDetailUtilisateurHabilitationsByActiveDirectoryLogin(String activeDirectoryLogin);

  UtilisateurHabilitationsDto getDetailUtilisateurHabilitationsRecoPassive(String numeroDossier);

  List<UtilisateurDetailDto> getDetailUtilisateurs(List<String> listeNumerosDossier);

  BitSet getCodeHabilitationsPourHabilitations(String listeHabilitations);

}