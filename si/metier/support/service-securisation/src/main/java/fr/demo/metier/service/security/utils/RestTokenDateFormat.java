package fr.demo.metier.service.security.utils;

import java.text.SimpleDateFormat;

public class RestTokenDateFormat {

  private static final String REST_TOKEN_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

  SimpleDateFormat dateFormat;

  public RestTokenDateFormat() {
    dateFormat = new SimpleDateFormat(REST_TOKEN_DATE_FORMAT);
  }

  public SimpleDateFormat getDateFormat() {
    return dateFormat;
  }

}
