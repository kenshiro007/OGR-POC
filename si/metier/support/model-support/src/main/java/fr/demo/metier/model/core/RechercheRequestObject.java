package fr.demo.metier.model.core;

import fr.demo.metier.model.validation.LimitPagination;
import fr.demo.metier.model.validation.RechercheExistenceCriteres;
import fr.demo.metier.model.validation.constraints.LimitMaxRechercheCvConstraint;
import fr.demo.metier.model.validation.constraints.LimitMaxRechercheProfilConstraint;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class RechercheRequestObject<X> {

  @NotNull(groups = RechercheExistenceCriteres.class)
  @Valid
  private X criteres;

  @Min(0)
  @NotNull(groups = {LimitPagination.class, RechercheExistenceCriteres.class})
  @Max(value = 500, groups = LimitPagination.class)
  @LimitMaxRechercheProfilConstraint(groups = RechercheExistenceCriteres.class)
  //TODO à supprimer
  @LimitMaxRechercheCvConstraint(groups = RechercheExistenceCriteres.class)
  private Integer limit;

  @Min(0)
  @NotNull(groups = {LimitPagination.class, RechercheExistenceCriteres.class})
  private Integer offset;


  public RechercheRequestObject(X criteres, Integer offset, Integer limit) {
    this.criteres = criteres;
    this.offset = offset;
    this.limit = limit;
  }

  public RechercheRequestObject(X criteres) {
    this.criteres = criteres;
    this.offset = null;
    this.limit = null;
  }

  public RechercheRequestObject() {
  }

  public X getCriteres() {
    return criteres;
  }

  public void setCriteres(X criteres) {
    this.criteres = criteres;
  }

  public Integer getLimit() {
    return limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  public Integer getOffset() {
    return offset;
  }

  public void setOffset(Integer offset) {
    this.offset = offset;
  }

}
