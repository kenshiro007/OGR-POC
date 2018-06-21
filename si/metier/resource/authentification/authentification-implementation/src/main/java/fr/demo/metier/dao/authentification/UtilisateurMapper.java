package fr.demo.metier.dao.authentification;

import fr.demo.metier.dao.core.impl.GenericMapper;
import fr.demo.metier.domain.authentification.Utilisateur;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface UtilisateurMapper extends GenericMapper<String, Utilisateur> {

  Utilisateur getUtilisateur(@Param("numeroDossier") String numeroDossier);

  List<Utilisateur> getUtilisateursBybounds(RowBounds rowBounds);

  Long  getcountUtilisateur();
}
