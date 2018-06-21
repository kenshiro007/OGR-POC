package fr.demo.metier.service.security.impl;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Component;

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import com.googlecode.concurrentlinkedhashmap.EvictionListener;

import fr.demo.metier.utils.AesUtils;
import fr.demo.metier.service.security.authentication.RestAuthenticationService;
import fr.demo.metier.service.security.authentication.TokenInfo;
import fr.demo.metier.service.security.authentication.TokenManager;
import fr.demo.metier.service.security.mbeans.RestSecurityMBean;
import fr.demo.metier.service.security.utils.AuthenticationUtils;
import fr.demo.metier.service.security.utils.RestTokenBase64;
import fr.demo.metier.service.security.utils.RestTokenDateFormat;
import fr.demo.metier.service.security.utils.SsoAuthenticationUtils;

@Component("restTokenManager")
public class RestTokenManager implements TokenManager {

  public static final String USER_ID = "userId";

  public static final String PASSWORD = "password";

  public static final String CREATED = "created";

  private static final Logger LOGGER = LoggerFactory.getLogger(RestTokenManager.class);

  private final Object tokenManagerMonitor = new Object();

  @Resource(name = "restSecurityMBean")
  RestSecurityMBean restSecurityMBean;

  @Resource(name = "restTokenDateFormat")
  RestTokenDateFormat restTokenDateFormat;

  @Resource(name = "ssoAuthenticationUtils")
  SsoAuthenticationUtils ssoAuthenticationUtils;

  @Resource(name = "restTokenBase64")
  RestTokenBase64 restTokenBase64;

  @Resource(name = "aesUtils")
  AesUtils aesUtils;

  @Value("${token.manager.validUsersCapacity}")
  private Long validUsersCapacity;

  @Value("${token.manager.dureeValiditeTokenHr}")
  private Integer dureeValiditeTokenHr;

  private Map<String, UserDetails> validUsers = new HashMap<>();

  private ConcurrentLinkedHashMap<UserDetails, TokenInfo> tokens;

  @PostConstruct
  public void initTokens() throws Exception {

    EvictionListener<UserDetails, TokenInfo> synchronousListener = new EvictionListener<UserDetails, TokenInfo>() {

      @Override
      public void onEviction(UserDetails key, final TokenInfo value) {
        for (String tokenToRemove : value.getTokens()) {
          removeToken(tokenToRemove);
        }
      }
    };

    tokens =
        new ConcurrentLinkedHashMap.Builder<UserDetails, TokenInfo>().maximumWeightedCapacity(
            validUsersCapacity).listener(synchronousListener).build();
  }

  @Override
  public void resetRestTokenManager() {

    synchronized (tokenManagerMonitor) {
      Long userCapacity = getUsersCapacity();
      setUsersCapacity(0L);
      setUsersCapacity(userCapacity);
    }
  }

  @Override
  public UserDetails getUserDetails(String token) {
    return validUsers.get(token);
  }

  /**
   * Création d'un token pour un utilisateur.
   *
   * @param userDetails
   * @return
   */
  @Override
  public String createNewToken(UserDetails userDetails) {

    LOGGER.debug("createNewToken({}))", userDetails);
    TokenInfo tokenInfo;
    String token;
    synchronized (tokenManagerMonitor) {
      do {
        token = encodeToken(userDetails);
        if (token == null) {
          LOGGER.debug("Encodage token impossible pour {}", userDetails);
          return null;
        }
      } while (validUsers.containsKey(token));
      TokenInfo previousToken = tokens.get(userDetails);
      if (previousToken != null) {
        previousToken.addToken(token);
        tokenInfo = previousToken;
      } else {
        tokenInfo = new TokenInfo(token, userDetails);
        tokens.put(userDetails, tokenInfo);
      }
      validUsers.put(token, tokenInfo.getUserDetails());
      return token;
    }
  }

  /**
   * Création d'un token SSO pour un utilisateur personnel.
   *
   * @param userDetails
   * @return
   */
  @Override
  public String createNewSsoToken(UserDetails userDetails) {

    LOGGER.debug("createNewSsoToken({}))", userDetails);
    TokenInfo tokenInfo;
    String token;
    synchronized (tokenManagerMonitor) {
      do {
        token = encodeSsoToken(userDetails);
        if (token == null) {
          LOGGER.debug("Encodage token impossible pour {}", userDetails);
          return null;
        }
      } while (validUsers.containsKey(token));
      TokenInfo previousToken = tokens.get(userDetails);
      if (previousToken != null) {
        previousToken.addToken(token);
        tokenInfo = previousToken;
      } else {
        tokenInfo = new TokenInfo(token, userDetails);
        tokens.put(userDetails, tokenInfo);
      }
      validUsers.put(token, tokenInfo.getUserDetails());
      return token;
    }
  }

  /**
   * Reactivation d'un token si encore valide (user/mdp et valide et délai de
   * validité non passé).
   *
   * @param token
   * @param authenticationService
   * @return
   */
  @Override
  public String reactivateToken(String token, RestAuthenticationService authenticationService) {

    LOGGER.debug("reactivateToken({}))", token);
    TokenInfo tokenInfo;
    synchronized (tokenManagerMonitor) {
      Date created = extractDateFromToken(token);
      if (created == null || !isStillValid(created)) {
        // Token périmé (délai de validité dépassé)
        LOGGER.debug("Token perimé : {} created : {}", token, created);
        return null;
      }
      Map<String, String> tokenInformations = decodeToken(token);
      if (tokenInformations == null) {
        // decodage impossible (token mal formé)
        LOGGER.debug("Token mal formé : {}", token);
        return null;
      }
      // on verifie que login/mdp sont encore valides :
      UserDetails userDetails =
          authenticationService.authenticate(tokenInformations.get(USER_ID), tokenInformations.get(PASSWORD));
      if (userDetails == null) {
        // authentification non valide
        LOGGER.debug("Mot de passe modifié depuis géneration du token  : {}", token);
        return null;
      }
      TokenInfo previousToken = tokens.get(userDetails);
      if (previousToken != null) {
        previousToken.addToken(token);
        tokenInfo = previousToken;
      } else {
        tokenInfo = new TokenInfo(token, userDetails);
        tokens.put(userDetails, tokenInfo);
      }
      validUsers.put(token, tokenInfo.getUserDetails());
      return token;
    }
  }

  /**
   * Reactivation d'un token SSO (toujours valide).
   *
   * @param token
   * @param authenticationService
   * @return
   */
  @Override
  public String reactivateSsoToken(String token, RestAuthenticationService authenticationService) {

    LOGGER.debug("reactivateSsoToken({}))", token);
    TokenInfo tokenInfo;
    synchronized (tokenManagerMonitor) {
      Map<String, String> tokenInformations = decodeSsoToken(token);
      if (tokenInformations == null) {
        // decodage impossible (token mal formé)
        LOGGER.debug("Token mal formé : {}", token);
        return null;
      }
      // on verifie que login/mdp sont encore valides :
      UserDetails userDetails =
          authenticationService.authenticate(
              ssoAuthenticationUtils.computePrincipalForSsoAuthentication(tokenInformations.get(USER_ID)),
              tokenInformations.get(PASSWORD));
      if (userDetails == null) {
        // authentification non valide
        LOGGER.debug("Mot de passe modifié depuis géneration du token  : {}", token);
        return null;
      }
      TokenInfo previousToken = tokens.get(userDetails);
      if (previousToken != null) {
        previousToken.addToken(token);
        tokenInfo = previousToken;
      } else {
        tokenInfo = new TokenInfo(token, userDetails);
        tokens.put(userDetails, tokenInfo);
      }
      validUsers.put(token, tokenInfo.getUserDetails());
      return token;
    }
  }

  /**
   * Utilisé pour les token 'sémantiques' : #token.reco.passive#numeroDossier#,
   * #token.generique#USER_ADEP#, #token.generique#VISI_FRONT#...
   *
   * @param token
   * @param userDetails
   */
  @Override
  public void insertToken(String token, UserDetails userDetails) {

    LOGGER.debug("insertToken({}, {})", token, userDetails);
    synchronized (tokenManagerMonitor) {
      TokenInfo previousToken = tokens.get(userDetails);
      if (previousToken == null) {
        previousToken = new TokenInfo(token, userDetails);
        tokens.put(userDetails, previousToken);
        validUsers.put(token, userDetails);
      }
    }
  }

  @Override
  public Integer getNumberOfUsers() {
    return tokens.size();
  }

  @Override
  public Integer getNumberOfToken() {
    return validUsers.size();
  }

  @Override
  public Long getUsersCapacity() {
    return tokens.capacity();
  }

  @Override
  public void setUsersCapacity(Long capacity) {
    tokens.setCapacity(capacity);
  }

  Date extractDateFromToken(String token) {
    String created;
    try {
      String encryptedString = new String(Base64.decode(token.getBytes()), StandardCharsets.UTF_8);
      String decrypedString = aesUtils.decrypt(encryptedString);
      created = decrypedString.substring(10, 29).trim();
      return restTokenDateFormat.getDateFormat().parse(created);
    } catch (Exception e) {
      LOGGER.debug("extractDateFromToken impossible : " + token, e);
      return null;
    }
  }

  boolean isStillValid(Date created) {

    Calendar endOfValidity = Calendar.getInstance();
    endOfValidity.setTime(created);
    endOfValidity.add(Calendar.HOUR, dureeValiditeTokenHr);
    Calendar now = Calendar.getInstance();
    return now.before(endOfValidity);
  }

  /**
   * Géneration d'un token incluant numeroDossier, date de création et mot de
   * passe (pour pouvoir le réactiver si besoin)
   *
   * @param userDetails
   * @return
   */
  String encodeToken(UserDetails userDetails) {

    LOGGER.debug("encodeToken({})", userDetails);
    byte[] tokenBytes = new byte[16];
    new SecureRandom().nextBytes(tokenBytes);
    Date created = new Date();
    try {
      String randomBuffer = new String(Base64.encode(tokenBytes), StandardCharsets.UTF_8);
      String encryptedString =
          aesUtils.encrypt(
              String.format(
                  AuthenticationUtils.TOKEN_ENCODING_FORMAT,
                  userDetails.getUsername(),
                  restTokenDateFormat.getDateFormat().format(created),
                  randomBuffer,
                  userDetails.getPassword()));
      LOGGER.debug("encrypted string : {}", encryptedString);
      return new String(Base64.encode(encryptedString.getBytes()), StandardCharsets.UTF_8);
    } catch (Exception e) {
      LOGGER.debug("EncodeToken error !", e);
      return null;
    }
  }

  /**
   * Géneration d'un token SSO incluant login active directory
   *
   * @param userDetails
   * @return
   */
  String encodeSsoToken(UserDetails userDetails) {

    LOGGER.debug("encodeSsoToken({})", userDetails);
    byte[] tokenBytes = new byte[16];
    new SecureRandom().nextBytes(tokenBytes);
    try {
      String randomBuffer = new String(Base64.encode(tokenBytes), StandardCharsets.UTF_8);
      String nom = ((RestUserSecurityContext) userDetails).getUser().getNom();
      String encryptedString =
          aesUtils.encrypt(
              String.format(
                  SsoAuthenticationUtils.TOKEN_ENCODING_FORMAT,
                  nom,
                  randomBuffer,
                  ssoAuthenticationUtils.computeEncryptedPassword(nom)));
      LOGGER.debug("encrypted sso string : {}", encryptedString);
      return new String(Base64.encode(encryptedString.getBytes()), StandardCharsets.UTF_8);
    } catch (Exception e) {
      LOGGER.debug("encodeSsoToken error !", e);
      return null;
    }
  }

  /**
   * Retourne les informations incluses dans le token passé.
   *
   * @param token
   * @return
   */
  Map<String, String> decodeToken(String token) {

    LOGGER.debug("decodeToken({})", token);
    Map<String, String> result = new HashMap<>();
    try {
      String encryptedString = new String(Base64.decode(token.getBytes()), StandardCharsets.UTF_8);
      String decrypedString = aesUtils.decrypt(encryptedString);
      result.put(USER_ID, decrypedString.substring(0, 10).trim());
      result.put(CREATED, decrypedString.substring(10, 29).trim());
      result.put(PASSWORD, decrypedString.substring(53));
    } catch (Exception e) {
      LOGGER.debug("decodeToken error !", e);
      return null;
    }
    return result;
  }

  /**
   * Retourne les informations incluses dans le token SSO passé.
   *
   * @param token
   * @return
   */
  Map<String, String> decodeSsoToken(String token) {

    LOGGER.debug("decodeSsoToken({})", token);
    Map<String, String> result = new HashMap<>();
    try {
      String encryptedString =
          new String(Base64.decode(token.getBytes()), StandardCharsets.UTF_8);
      String decrypedString = aesUtils.decrypt(encryptedString);
      result.put(USER_ID, decrypedString.substring(0, 10).trim());
      result.put(PASSWORD, decrypedString.substring(34));
    } catch (Exception e) {
      LOGGER.debug("decodeSsoToken error !", e);
      return null;
    }
    return result;
  }

  UserDetails removeToken(String token) {

    LOGGER.debug("removeToken({})", token);
    synchronized (tokenManagerMonitor) {
      UserDetails userDetails = validUsers.remove(token);
      return userDetails;
    }
  }

}
