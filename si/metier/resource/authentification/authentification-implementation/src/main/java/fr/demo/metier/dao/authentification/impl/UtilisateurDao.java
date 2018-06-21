package fr.demo.metier.dao.authentification.impl;

import fr.demo.metier.dao.authentification.UtilisateurMapper;
import fr.demo.metier.dao.core.impl.MyBatisGenericMapperDao;
import fr.demo.metier.domain.authentification.Utilisateur;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("authentification.utilisateurDao")
public class UtilisateurDao extends MyBatisGenericMapperDao<String, Utilisateur, UtilisateurMapper> {

  public UtilisateurDao() {
    super(UtilisateurMapper.class);
  }
  public List<Utilisateur> getUtilisateursBybounds(int debut , int fin) {
    RowBounds rowBounds = new RowBounds(debut,fin);
    SqlSession session = getSqlSession();
    return session.getMapper(getBasicMapper()).getUtilisateursBybounds(rowBounds);
  }
  public Utilisateur getUtilisateur(String numeroDossier) {
    SqlSession session = getSqlSession();
    return session.getMapper(getBasicMapper()).getUtilisateur(numeroDossier);
  }
  public long getcountUsers() {
    SqlSession session = getSqlSession();
    return session.getMapper(getBasicMapper()).getcountUtilisateur();
  }

}
