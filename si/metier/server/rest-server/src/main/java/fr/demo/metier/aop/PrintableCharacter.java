package fr.demo.metier.aop;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import fr.demo.metier.model.core.Auditable;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;


@Aspect
public class PrintableCharacter {
  private static final Logger LOGGER = LoggerFactory.getLogger(PrintableCharacter.class);

  @Before("execution(@fr.demo.metier.rest.core.annotation.NonPrintableCharFilter * *.*(..))")
  public void filterOperations(JoinPoint joinPoint) throws Throwable {
    LOGGER.debug("filterOperations");
    process(joinPoint);
  }

  private void process(JoinPoint joinPoint) throws InvocationTargetException, IllegalAccessException, IntrospectionException {
    for (Object o : joinPoint.getArgs()) {
      recursiveStringFilter(o);
    }
  }

  private void recursiveStringFilter(Object o) throws IntrospectionException, IllegalAccessException, InvocationTargetException {
    if (o instanceof Auditable) {
      ImmutableList<Field> filteredFields = ImmutableList.copyOf(Iterables.filter(ImmutableList.copyOf(
        o.getClass().getDeclaredFields()), new Predicate<Field>() {
        @Override
        public boolean apply(@Nullable Field input) {
          return input != null && (input.getType().equals(String.class) || input.getType().isAssignableFrom(List.class) ||
            input.getType().isAssignableFrom(Auditable.class));
        }
      }));
      for (Field field : filteredFields) {
        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), o.getClass());
        if (field.getType().equals(String.class)) {
          String value = (String) propertyDescriptor.getReadMethod().invoke(o);
          if (value != null) {
            propertyDescriptor.getWriteMethod().invoke(o, nonPrintableCharacterFilter(value));
          }
        } else {
          recursiveStringFilter(propertyDescriptor.getReadMethod().invoke(o));
        }
      }
    }
    if (o instanceof List) {
      for (Object o2 : (List) o) {
        recursiveStringFilter(o2);
      }
    }
  }

  private String nonPrintableCharacterFilter(String input) {
    char[] oldChars = new char[5];
    final int length = input.length();
    if (oldChars.length < length) {
      oldChars = new char[length];
    }
    input.getChars(0, length, oldChars, 0);
    int newLen = 0;
    for (int j = 0; j < length; j++) {
      char ch = oldChars[j];
      if (ch >= ' ') {
        oldChars[newLen] = ch;
        newLen++;
      }
    }
    if (newLen != length) {
      return new String(oldChars, 0, newLen);
    } else {
      return input;
    }
  }

}
