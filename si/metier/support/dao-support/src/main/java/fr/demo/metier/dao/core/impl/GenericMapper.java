package fr.demo.metier.dao.core.impl;

import fr.demo.metier.model.core.GenericIdObject;

import java.io.Serializable;
import java.util.List;

public interface GenericMapper<PK extends Serializable, E extends GenericIdObject<PK>> {

  int create(E entity);

  int delete(E entity);

  int deleteById(PK id);

  Boolean exists(PK id);

  List<E> findAll();

  List<PK> findAllIds();

  E get(PK id);

  int update(E entity);

}
