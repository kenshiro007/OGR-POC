package fr.demo.metier.utils;

import org.springframework.beans.factory.annotation.Value;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.Security;


public class AesUtils {

  private static final String ALGO = "AES";

  @Value("${aes.utils.secret64.128key}")
  private String secret64;

  BASE64Encoder base64Encoder;
  BASE64Decoder base64Decoder;

  public void setBase64Encoder(BASE64Encoder base64Encoder) {
    this.base64Encoder = base64Encoder;
  }

  public void setBase64Decoder(BASE64Decoder base64Decoder) {
    this.base64Decoder = base64Decoder;
  }

  /**
   * Encryption AES 128bits ECB/PKCS7 Padding
   * @param data
   * @return
   * @throws Exception
   */
  public String encrypt(String data) throws Exception {

    Key key = generateKeyFromString(secret64);
    if (Security.getProvider("BC") == null) {
      Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }
    Cipher c = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");
    c.init(Cipher.ENCRYPT_MODE, key);
    byte[] encVal = c.doFinal(data.getBytes());
    return base64Encoder.encode(encVal);
  }

  /**
   * Decryption AES 128bits ECB/PKCS7 Padding
   * @param encryptedData
   * @return
   * @throws Exception
   */
  public String decrypt(String encryptedData) throws Exception {

    Key key = generateKeyFromString(secret64);
    if (Security.getProvider("BC") == null) {
      Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }
    Cipher c = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");
    c.init(Cipher.DECRYPT_MODE, key);
    byte[] decordedValue = base64Decoder.decodeBuffer(encryptedData);
    byte[] decValue = c.doFinal(decordedValue);
    return new String(decValue);
  }

  /**
   * Génère une clé 128bits pour AES
   *
   * @return
   * @throws Exception
   */
  public static Key generateKey() throws Exception {
    KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGO, "BC");
    keyGenerator.init(128);
    return keyGenerator.generateKey();
  }

  Key generateKeyFromString(final String secretKey64) throws Exception {
    final byte[] keyVal = base64Decoder.decodeBuffer(secretKey64);
    return new SecretKeySpec(keyVal, ALGO);
  }

}
