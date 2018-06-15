package fr.demo.metier.rest.core.provider;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ErreursValidation {

  @XmlElement
  List<XMLError> erreur;


  public ErreursValidation() {

  }

  public ErreursValidation(List<XMLError> erreur) {
    this.erreur = erreur;
  }

  public List<XMLError> getErreur() {
    if(this.erreur == null){
      this.erreur = new ArrayList<>();
    }
    return erreur;
  }

  public void setErreur(List<XMLError> erreur) {
    this.erreur = erreur;
  }
}
