package fr.demo.metier.model.core;

public interface Auditable {
  AuditObject getAudit();
  void setAudit(AuditObject audit);
}
