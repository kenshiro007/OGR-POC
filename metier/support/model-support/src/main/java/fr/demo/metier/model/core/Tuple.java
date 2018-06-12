package fr.demo.metier.model.core;

import java.io.Serializable;

public class Tuple<X, Y> implements Serializable{
  
  /** serialVersionUID */
  private static final long serialVersionUID = 1L;

  private transient X first;

  private transient Y second;

  public Tuple() {
    //Nothing to do
  }
  
  public Tuple(X first, Y second) {
    this.first = first;
    this.second = second;
  }

  public X getFirst() {
    return first;
  }

  public void setFirst(X first) {
    this.first = first;
  }

  public Y getSecond() {
    return second;
  }

  public void setSecond(Y second) {
    this.second = second;
  }

}
