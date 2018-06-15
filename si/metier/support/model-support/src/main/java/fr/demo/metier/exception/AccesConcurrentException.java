package fr.demo.metier.exception;

import fr.demo.metier.model.core.GenericIdObject;

import java.io.Serializable;

public class AccesConcurrentException extends RuntimeException {

  private final String canonicalName;

  private final Serializable idObject;
  
  public AccesConcurrentException(String message) {
    super(message);
    canonicalName = null;
    idObject = null;
  }
  
  public AccesConcurrentException(GenericIdObject<?> entity) {
    super("Conflit de mise à jour !");
    canonicalName = entity.getClass().getCanonicalName();
    idObject = entity.getId();
  }

  public String getCanonicalName() {
    return canonicalName;
  }

  public Serializable getIdObject() {
    return idObject;
  }
  
}
