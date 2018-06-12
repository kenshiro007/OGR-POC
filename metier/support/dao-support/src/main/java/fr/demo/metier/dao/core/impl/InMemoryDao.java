package fr.demo.metier.dao.core.impl;

import fr.demo.metier.dao.core.Dao;
import fr.demo.metier.model.core.IdObject;

import java.util.*;

public class InMemoryDao<E extends IdObject> implements Dao<E, Long> {

  private Long lastId;

  private Map<Long, E> map = new HashMap<Long, E>();

  @Override
  public E create(E entity) {
    lastId = getLastId() + 1;
    entity.setId(lastId);
    map.put(entity.getId(), entity);
    return entity;
  }

  @Override
  public int delete(E entity) {
    return map.remove(entity.getId()) == null ? 0 : 1;

  }

  @Override
  public int deleteById(Long id) {
    return map.remove(id) == null ? 0 : 1;

  }

  @Override
  public List<E> findAll() {
    return getDatas();
  }

  @Override
  public List<Long> findAllIds() {
    List<Long> temp = new LinkedList<Long>();
    for (E entity : getDatas()) {
      temp.add(entity.getId());
    }
    return temp;
  }

  public void flush() {
    // Pour faire plaisir a Sonar on met un commentaire parce que la methode est vide...
  }

  @Override
  public E get(Long id) {
    for (E entity : map.values()) {
      if (entity.getId().equals(id)) {
        return entity;
      }
    }
    return null;
  }

  public List<E> getDatas() {
    return new ArrayList<E>(map.values());
  }

  public void setDatas(List<E> datas) {
    Map<Long, E> temp = new HashMap<Long, E>();
    for (E entity : datas) {
      temp.put(entity.getId(), entity);
    }
    map = temp;
  }

  public Long getLastId() {
    if (lastId == null) {
      List<E> list = findAll();
      lastId = 0L;
      for (E item : list) {
        if (item.getId() > lastId) {
          lastId = item.getId();
        }
      }
    }
    return lastId;
  }

  @Override
  public E merge(E entity) {
    return update(entity);
  }

  @Override
  public void persist(E entity) {
    create(entity);
  }

  @Override
  public E update(E entity) {
    map.put(entity.getId(), entity);
    return entity;
  }

}
