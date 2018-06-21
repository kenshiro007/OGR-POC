package fr.demo.metier.aop;

import fr.demo.metier.exception.BadRequestException;
import fr.demo.metier.model.core.AuditObject;
import fr.demo.metier.model.core.Auditable;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class AuditablePropagation {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(AuditablePropagation.class);
  
  private ThreadLocal<AuditObject> localAuditObject = new ThreadLocal<>();

  @Around("execution(* *.*(..)) && @annotation(fr.demo.metier.rest.core.annotation.AuditablePropagationCreation)")
  public Object beforeAuditablePropagationCreation(ProceedingJoinPoint joinPoint) throws Throwable {
    extractAndValidateAudit(joinPoint, true);
    try {
      return joinPoint.proceed();
    } finally {
      localAuditObject.remove();
    }
  }
  
  @Around("execution(* *.*(..)) && @annotation(fr.demo.metier.rest.core.annotation.AuditablePropagationModification)")
  public Object beforeAuditablePropagationModification(ProceedingJoinPoint joinPoint) throws Throwable {
    extractAndValidateAudit(joinPoint, false);
    try {
      return joinPoint.proceed();
    } finally {
      localAuditObject.remove();
    }
  }
  
  @Before("execution(public * fr.demo.metier.dao.core.impl.MyBatis*MapperDao+.*(..))")
  public void beforeMapperOperations(JoinPoint joinPoint) {
    for (Object o: joinPoint.getArgs()) {
      if (o instanceof Auditable) {
        if (localAuditObject.get() == null) {
          throw new BadRequestException("Aucun AuditObject master : il doit manquer l'annotation AuditablePropagation");
        }
        ((Auditable)o).setAudit(localAuditObject.get());
      }
    }
  }
  
  private void extractAndValidateAudit(JoinPoint joinPoint, boolean isCreation) {
    AuditObject master = null;
    boolean auditableFound = false;
    for (Object o: joinPoint.getArgs()) {
      if (o instanceof Auditable) {
        auditableFound = true;
        Auditable auditable = (Auditable)o;
        master = auditable.getAudit();
        if (master != null) {
          break;
        }
      }
    }
    if (!auditableFound) {
      LOGGER.error("La méthode {} est annotée AuditablePropagation mais aucun Auditable n'a été trouvé !", joinPoint.getSignature());
    }
    if (master == null) {
      throw new BadRequestException("AuditObject master est null !");
    }
    // Cas particuliers où, par exemple, on peut être amené à créer un objet enfant lors d'un update
    if (isCreation) {
      master.setUtilisateurModification(master.getUtilisateurCreation());
    } else {
      master.setUtilisateurCreation(master.getUtilisateurModification());
    }
    if (master.getUtilisateurCreation() == null) {
      throw new BadRequestException("AuditObject mal renseigné !");
    }
    localAuditObject.set(master);
  }
}
