package fr.demo.metier.service.security.utils;

import fr.demo.metier.utils.AesUtils;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SsoAuthenticationUtils {
	
  private static final Logger LOGGER = LoggerFactory.getLogger(SsoAuthenticationUtils.class);

  public static final String SSO_HEADER_TOKEN = "SSO-Auth-Token";

  public static final String SSO_HEADER_USERNAME = "SSO-Username";

  public static final String SSO_HEADER_PASSWORD = "SSO-Password";

  public static final String SSO_PASSWORD_PATTERN_FORMAT = "-*-s3cr37p4ssw0rdForm47-*+%s/*-==";

  private static final String ACTIVE_DIRECTORY_LOGIN_PREFIX = "active-directory-login:";

  public static final String TOKEN_ENCODING_FORMAT = "%-10s%s%s";

  @Resource(name = "aesUtils")
  AesUtils aesUtils;

  String computePasswordPattern(String username) {
    return String.format(SSO_PASSWORD_PATTERN_FORMAT, username);
  }

  boolean matchPasswordPatternFormat(String decryptedPasswordPatternFormat, String username) {
    if (decryptedPasswordPatternFormat != null) {
      return decryptedPasswordPatternFormat.equals(String.format(SSO_PASSWORD_PATTERN_FORMAT, username));
    } else {
      return false;
    }
  }

  public String computeEncryptedPassword(String username) {
    try {
      return aesUtils.encrypt(computePasswordPattern(username));
    } catch (Exception e) {
      LOGGER.error("Exception computeEncryptedPassword !", e);
      return null;
    }
  }

  public boolean isPasswordOk(String password, String username) {
    try {
      String decryptedPassword = aesUtils.decrypt(password);
      return matchPasswordPatternFormat(decryptedPassword, username);
    } catch (Exception e) {
      LOGGER.error("Exception isPasswordOk !", e);
      return false;
    }
  }

  public boolean isSsoAuthentication(String principal) {
    return principal.startsWith(ACTIVE_DIRECTORY_LOGIN_PREFIX);
  }

  public String getActiveDirectoryLoginFromSsoAuthentication(String principal) {
    return principal.substring(ACTIVE_DIRECTORY_LOGIN_PREFIX.length());
  }

  public String computePrincipalForSsoAuthentication(String principal) {
    return ACTIVE_DIRECTORY_LOGIN_PREFIX + principal;
  }

}
