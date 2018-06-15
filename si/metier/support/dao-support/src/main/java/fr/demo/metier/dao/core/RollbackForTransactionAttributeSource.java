package fr.demo.metier.dao.core;

import fr.demo.metier.exception.ValidationException;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.interceptor.DelegatingTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;

import java.lang.reflect.AnnotatedElement;

public class RollbackForTransactionAttributeSource extends AnnotationTransactionAttributeSource {
  @Override
  protected TransactionAttribute determineTransactionAttribute(AnnotatedElement ae) {
    TransactionAttribute target = super.determineTransactionAttribute(ae);
    if (target == null) {
      return null;
    }
    return new DelegatingTransactionAttribute(target) {
      @Override
      public boolean rollbackOn(Throwable ex) {
        return ex instanceof RuntimeException || ex instanceof ValidationException;
      }
    };
  }
}
