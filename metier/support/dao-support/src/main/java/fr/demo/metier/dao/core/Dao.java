package fr.demo.metier.dao.core;

import java.io.Serializable;
import java.util.List;

public interface Dao<E, PK extends Serializable> {

  // On utilisera soit les apis persist/merge soit les apis create/update en
  // fonction de la technologie sous jacente

  // ou persist
  E create(E entity);

  int delete(E entity);

  int deleteById(PK id);

  // A utiliser avec précaution
  List<E> findAll();

  List<PK> findAllIds();

  // technical use
  void flush();

  E get(PK id);

  // ou update ?
  E merge(E entity);

  // ou create
  void persist(E entity);

  // ou merge ?
  E update(E entity);
}
