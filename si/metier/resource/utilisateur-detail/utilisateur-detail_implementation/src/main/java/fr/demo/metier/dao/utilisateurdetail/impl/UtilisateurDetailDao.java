package fr.demo.metier.dao.utilisateurdetail.impl;

import fr.demo.metier.dao.core.impl.MyBatisGenericMapperDao;
import fr.demo.metier.dao.utilisateurdetail.UtilisateurDetailMapper;
import fr.demo.metier.domain.utilisateurdetail.UtilisateurDetail;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("utilisateurdetail.utilisateurDetailDao")
public class UtilisateurDetailDao extends MyBatisGenericMapperDao<String, UtilisateurDetail, UtilisateurDetailMapper> {

  public UtilisateurDetailDao() {
    super(UtilisateurDetailMapper.class);
  }

  public List<UtilisateurDetail> getUtilisateurs(List<String> listeNumerosDossier) {
    SqlSession session = getSqlSession();
    return session.getMapper(getBasicMapper()).getUtilisateurs(listeNumerosDossier);
  }

}
