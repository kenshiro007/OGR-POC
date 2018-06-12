package fr.demo.metier.model.core;

import java.util.List;

public class RechercheResultObjectData<X, Y> extends RechercheResultObject<X> {

  private Y data;

  public RechercheResultObjectData(Long count, List<X> results, Y data) {
    super(count, results);
    this.data = data;
  }
  
  public RechercheResultObjectData(RechercheResultObject<X> resultObject, Y data) {
    this(resultObject.getCount(), resultObject.getResults(), data);
  }

  public RechercheResultObjectData() {
  }

  public Y getData() {
    return data;
  }

  public void setData(Y data) {
    this.data = data;
  }

}
