package fr.demo.metier.model.core;

import java.io.Serializable;

public class RapportExceptionDto {

  private String message;

  private String stackTrace;

  private String webService;

  private String operation;

  private Object args;

  private String canonicalName;

  private Serializable idObject;

  public RapportExceptionDto() {
  }

  public RapportExceptionDto(String message, String stackTrace) {
    this.message = message;
    this.stackTrace = stackTrace;
  }

  public RapportExceptionDto(String message, String stackTrace, String webService, String operation, Object args) {
    this(message, stackTrace);
    this.webService = webService;
    this.operation = operation;
    this.args = args;
  }

  public RapportExceptionDto(String message, String stackTrace, String canonicalName, Serializable idObject) {
    this(message, stackTrace);
    this.canonicalName = canonicalName;
    this.idObject = idObject;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getStackTrace() {
    return stackTrace;
  }

  public void setStackTrace(String stackTrace) {
    this.stackTrace = stackTrace;
  }

  public String getWebService() {
    return webService;
  }

  public void setWebService(String prestataire) {
    this.webService = prestataire;
  }

  public String getOperation() {
    return operation;
  }

  public void setOperation(String operation) {
    this.operation = operation;
  }

  public Object getArgs() {
    return args;
  }

  public void setArgs(Object args) {
    this.args = args;
  }

  public String getCanonicalName() {
    return canonicalName;
  }

  public void setCanonicalName(String canonicalName) {
    this.canonicalName = canonicalName;
  }

  public Serializable getIdObject() {
    return idObject;
  }

  public void setIdObject(Serializable id) {
    this.idObject = id;
  }

}
