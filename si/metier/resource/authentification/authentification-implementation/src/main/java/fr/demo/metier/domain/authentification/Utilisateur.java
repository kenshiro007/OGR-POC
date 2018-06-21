package fr.demo.metier.domain.authentification;

import fr.demo.metier.model.core.AuditObject;
import fr.demo.metier.model.core.Auditable;
import fr.demo.metier.model.core.GenericIdObject;
import fr.demo.metier.model.core.VersionObject;

import java.io.Serializable;
import java.util.Date;

public class Utilisateur implements Serializable, VersionObject, Auditable, GenericIdObject<String> {

  private static final long serialVersionUID = 1L;

  protected AuditObject audit;

  private Long numeroVersion;

  private String numeroDossier;

  private String motDePasse;

  private String motDePasseSha512;

  private String motDePasseSel;

  private Date dateExpiration;
  @Override
  public String getId() {
    return numeroDossier;
  }

  @Override
  public void setId(String numeroDossier) {
    this.numeroDossier = numeroDossier;
  }

  @Override
  public AuditObject getAudit() {
    return audit;
  }

  @Override
  public void setAudit(AuditObject audit) {
    this.audit = audit;
  }

  @Override
  public Long getNumeroVersion() {
    return numeroVersion;
  }

  @Override
  public void setNumeroVersion(Long numeroVersion) {
    this.numeroVersion = numeroVersion;
  }

  public String getMotDePasse() {
    return motDePasse;
  }

  public void setMotDePasse(String motDePasse) {
    this.motDePasse = motDePasse;
  }

  public String getMotDePasseSha512() {
    return motDePasseSha512;
  }

  public void setMotDePasseSha512(String motDePasseSha512) {
    this.motDePasseSha512 = motDePasseSha512;
  }

  public String getMotDePasseSel() {
    return motDePasseSel;
  }

  public void setMotDePasseSel(String motDePasseSel) {
    this.motDePasseSel = motDePasseSel;
  }

  public Date getDateExpiration() {
    return dateExpiration;
  }

  public void setDateExpiration(Date dateExpiration) {
    this.dateExpiration = dateExpiration;
  }
}
