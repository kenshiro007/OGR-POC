package fr.demo.metier.service.security.utils;

import org.springframework.security.crypto.codec.Base64;

/**
 * Created by pleroux on 09/07/2015.
 */
public class RestTokenBase64 {

  Base64 encoder;

  public RestTokenBase64() {
    encoder = new Base64();
  }

  public Base64 getEncoder() {
    return encoder;
  }

}
