package fr.demo.metier.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class Sha512Utils {

  private static final String ALGORITHM = "SHA-512";
  private static final Logger LOGGER = LoggerFactory.getLogger(Sha512Utils.class);

  /**
   * Chiffre une chaine de caractere en SHA-512.
   *
   * @param message   message à chiffrer
   * @param salt      sel à utiliser
   * @return le message chiffré
   */
  public String encrypt(String message, String salt) {

    // Récupère l'instance MessageDigest pour l'algorithme de hachage SHA-512 (64 bits)
    MessageDigest md;
    try {
      md = MessageDigest.getInstance(ALGORITHM);
    } catch (NoSuchAlgorithmException e) {
      LOGGER.error(e.getMessage(), e);
      throw new RuntimeException(e);
    }

    // Chiffre un message avec un sel, résultat dans un tableau de bytes
    md.update(salt.getBytes());
    byte[] bytes = md.digest(message.getBytes());

    // Encode le tableau de bytes en une chaîne de 128 caractères
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < bytes.length; ++i) {
      sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
    }
    return sb.toString();
  }

  /**
   * Chiffre le sel de mot de passe d'un utilisateur à partir de sa date de création et de son numéro de dossier.
   * @param dateCreation  date de création de l'utilisateur
   * @param numeroDossier numéro de dossier de l'utilisateur
   * @return le sel chiffré
   */
  public String encryptUserPasswordSalt(Date dateCreation, String numeroDossier) {
    return encrypt("" + numeroDossier + dateCreation.getTime(), numeroDossier);
  }
}
