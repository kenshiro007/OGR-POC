package fr.demo.metier.model.referentielstatique;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import fr.demo.metier.model.core.IdObject;
import fr.demo.metier.model.utils.IdObjectEnumUtils;

public enum EnumReclamationStatus implements IdObject {
  OUVERT(10001L),
  TRAITE(10002L),
  AFFECTEE(10003L),
  FERME(10004L);

  private Long id;

  EnumReclamationStatus(Long id) {
    this.id = id;
  }

  @JsonCreator
  public static EnumReclamationStatus fromId(Long id) {
    // Lancer une exception si c'est null ?
    return IdObjectEnumUtils.fromId(EnumReclamationStatus.class, id);
  }

  @Override
  @JsonValue
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    throw new UnsupportedOperationException();
  }

}
