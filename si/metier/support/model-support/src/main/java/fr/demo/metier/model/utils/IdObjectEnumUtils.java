package fr.demo.metier.model.utils;

import fr.demo.metier.model.core.GenericIdObject;

import java.io.Serializable;

public class IdObjectEnumUtils {
  
  protected IdObjectEnumUtils() {
  }
  
  public static <T extends Enum<T> & GenericIdObject<? extends Serializable>> T fromId(Class<T> enumType, Serializable id) {
    if (id != null) {
      for (T e : enumType.getEnumConstants()) {
        if (id.equals(e.getId())) {
          return e;
        }
      }
    }
    return null;
  }

}
