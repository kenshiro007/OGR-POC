package fr.demo.metier.model.utils;

public class UrlUtils {
  
  private UrlUtils(){
    throw new AssertionError();
  }

  public static String var(String name) {
    return "{" + name + "}";
  }

  public static String param(String name) {
    return name + "={" + name + "}";
  }
  
  public static String path(String url, String addition) {
    String result = null;
    if (addition == null || addition.isEmpty()) {
      return url;
    }

    switch (addition.charAt(0)) {
    case '/':
    case '?':
      result = url + addition;
      break;
    default:
      result = url + "/" + addition;
    }
    return result;
  }
  
}
