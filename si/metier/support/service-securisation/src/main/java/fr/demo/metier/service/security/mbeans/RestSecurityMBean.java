package fr.demo.metier.service.security.mbeans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import fr.demo.metier.service.security.impl.RestTokenManager;

@Component("restSecurityMBean")
@ManagedResource(objectName = "fr.demo.bean:name=restSecurity")
public class RestSecurityMBean {

  @Autowired
  RestTokenManager restTokenManager;

  @ManagedOperation
  public long getRestTokenManagerCapacity() {
    return restTokenManager.getUsersCapacity();
  }

  @ManagedOperation
  public void setRestTokenManagerCapacity(long capacity) {
    restTokenManager.setUsersCapacity(capacity);
  }

  @ManagedOperation
  public void resetRestTokenManager() {
    restTokenManager.resetRestTokenManager();
  }


  @ManagedAttribute
  public int getNumberOfToken() {
    return restTokenManager.getNumberOfToken();
  }

  @ManagedAttribute
  public int getNumberOfUsers() {
    return restTokenManager.getNumberOfUsers();
  }

}
