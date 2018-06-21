package fr.demo.metier.dao.utilisateurdetail;

import fr.demo.metier.dao.core.impl.GenericMapper;
import fr.demo.metier.domain.utilisateurdetail.UtilisateurDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UtilisateurDetailMapper extends GenericMapper<String, UtilisateurDetail> {

  List<UtilisateurDetail> getUtilisateurs(@Param("listeNumerosDossier") List<String> listeNumerosDossier);

}
