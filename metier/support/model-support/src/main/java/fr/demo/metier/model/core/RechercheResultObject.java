package fr.demo.metier.model.core;

import java.util.ArrayList;
import java.util.List;

public class RechercheResultObject<X> {

  private Long count;

  private List<X> results = new ArrayList<X>();

  public RechercheResultObject(Long count, List<X> results) {
    this.count = count;
    this.results = results;
  }

  public RechercheResultObject() {
  }

  public Long getCount() {
    return count;
  }

  public List<X> getResults() {
    return results;
  }

  public void setCount(Long count) {
    this.count = count;
  }

  public void setResults(List<X> results) {
    this.results = results;
  }

}
