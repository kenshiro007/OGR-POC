package fr.demo.metier.dao.core.impl;

import fr.demo.metier.model.core.IdObject;

public class MyBatisMapperDao<E extends IdObject, M extends BasicMapper<E>> extends MyBatisGenericMapperDao<Long, E, M> {

  public MyBatisMapperDao(Class<M> basicMapper) {
    super(basicMapper);
  }

}