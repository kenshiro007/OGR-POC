package fr.demo.metier.dao.core.impl;

import fr.demo.metier.dao.core.Dao;
import fr.demo.metier.exception.AccesConcurrentException;
import fr.demo.metier.model.core.GenericIdObject;
import fr.demo.metier.model.core.VersionObject;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.Serializable;
import java.util.List;

public class MyBatisGenericMapperDao<PK extends Serializable, E extends GenericIdObject<PK>, M extends GenericMapper<PK, E>>
    extends
      SqlSessionDaoSupport implements Dao<E, PK> {

  private Class<M> basicMapper;

  public MyBatisGenericMapperDao(Class<M> basicMapper) {
    this.basicMapper = basicMapper;
  }

  public E create(E entity) {
    SqlSession session = getSqlSession();
    session.getMapper(getBasicMapper()).create(entity);
    return entity;
  }

  public int delete(E entity) {
    SqlSession session = getSqlSession();
    int res = session.getMapper(getBasicMapper()).delete(entity);
    verifUpdate(res, entity);
    return res;
  }

  public int deleteById(PK id) {
    SqlSession session = getSqlSession();
    return session.getMapper(getBasicMapper()).deleteById(id);
  }

  public Boolean exists(PK id) {
    SqlSession session = getSqlSession();
    return session.getMapper(getBasicMapper()).exists(id);
  }

  public List<E> findAll() {
    SqlSession session = getSqlSession();
    return session.getMapper(getBasicMapper()).findAll();
  }

  public List<PK> findAllIds() {
    SqlSession session = getSqlSession();
    return session.getMapper(getBasicMapper()).findAllIds();
  }

  public void flush() {
    //interdit car géré par spring
  }

  public E get(PK id) {
    SqlSession session = getSqlSession();
    return session.getMapper(getBasicMapper()).get(id);
  }

  public Class<M> getBasicMapper() {
    return basicMapper;
  }

  @Override
  public E merge(E entity) {
    return update(entity);
  }

  @Override
  public void persist(E entity) {
    create(entity);
  }

  @Autowired
  @Qualifier("sqlSessionFactory")
  public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
    super.setSqlSessionFactory(sqlSessionFactory);
  }

  public E update(E entity) {
    SqlSession session = getSqlSession();
    int res = session.getMapper(getBasicMapper()).update(entity);
    verifUpdate(res, entity);
    return entity;
  }

  protected <T extends GenericIdObject<PK>> void verifUpdate(int res, T entity) {
    // Si l'entity implémente VersionObject il faut s'assurer que l'objet a bien
    // été mis à jour
    if (res == 0 && entity instanceof VersionObject) {
      throw new AccesConcurrentException(entity);
    }
  }
}