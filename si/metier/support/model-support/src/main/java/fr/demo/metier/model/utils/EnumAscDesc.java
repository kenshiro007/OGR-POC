package fr.demo.metier.model.utils;

public enum EnumAscDesc {
  ASC(true),
  DESC(false);
  
  private boolean asc;

  EnumAscDesc(boolean asc) {
    this.setAsc(asc);
  }

  public boolean isAsc() {
    return asc;
  }

  public void setAsc(boolean asc) {
    this.asc = asc;
  }
}
