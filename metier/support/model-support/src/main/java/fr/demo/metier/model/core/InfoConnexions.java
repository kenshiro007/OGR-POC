package fr.demo.metier.model.core;

import java.io.Serializable;
import java.util.Date;

public class InfoConnexions implements Serializable {

  private static final long serialVersionUID = 1717886882631564290L;

  private Date dateDerniereConnexion;

  private Date datePremiereConnexion;

  private Boolean indicateurNpmi;

  private Long nombreConnexions;

  public Date getDateDerniereConnexion() {
    return dateDerniereConnexion;
  }

  public Date getDatePremiereConnexion() {
    return datePremiereConnexion;
  }

  public Long getNombreConnexions() {
    return nombreConnexions;
  }

  public void setDateDerniereConnexion(Date dateDerniereConnexion) {
    this.dateDerniereConnexion = dateDerniereConnexion;
  }

  public void setDatePremiereConnexion(Date datePremiereConnexion) {
    this.datePremiereConnexion = datePremiereConnexion;
  }

  public void setNombreConnexions(Long nombreConnexions) {
    this.nombreConnexions = nombreConnexions;
  }

  public Boolean getIndicateurNpmi() {
    return indicateurNpmi;
}

  public void setIndicateurNpmi(Boolean indicateurNpmi) {
    this.indicateurNpmi = indicateurNpmi;
  }

}
