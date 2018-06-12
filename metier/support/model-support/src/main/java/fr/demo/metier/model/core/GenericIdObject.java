package fr.demo.metier.model.core;

import java.io.Serializable;

public interface GenericIdObject<PK extends Serializable> {

  PK getId();

  void setId(PK id);

}
