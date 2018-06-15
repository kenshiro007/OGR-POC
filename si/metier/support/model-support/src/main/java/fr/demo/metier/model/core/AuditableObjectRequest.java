package fr.demo.metier.model.core;

import javax.validation.Valid;


public class AuditableObjectRequest<T extends Object> implements Auditable {

  @Valid
  private AuditObject audit;
  
  @Valid
  private T object;
  
  @Override
  public AuditObject getAudit() {
    return audit;
  }

  @Override
  public void setAudit(AuditObject audit) {
    this.audit = audit;
  }

  public T getObject() {
    return object;
  }

  public void setObject(T object) {
    this.object = object;
  }

}
