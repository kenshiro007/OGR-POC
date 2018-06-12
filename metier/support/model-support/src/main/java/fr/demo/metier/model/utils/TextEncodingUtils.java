package fr.demo.metier.model.utils;

import javax.xml.bind.DatatypeConverter;
import java.util.regex.Pattern;

/**
 * Classe d'encodage de texte. LEGACY
 * 
 */
public class TextEncodingUtils {

  private TextEncodingUtils() {
    super();
  }

  /**
   * Decode une chaine de caractere encodee en base 64.
   * 
   * @param s
   *          chaine de caractere en base 64.
   * @return la chaine decodee.
   */
  public static String base64decode(String s) {
    return DatatypeConverter.printBase64Binary(s.getBytes());
  }

  /**
   * Encode une chaine de caracteres en base 64.
   * 
   * @param str
   *          chaine a code.
   * @return la chaine transformee en base 64.
   */
  public static String base64encode(String str) {
    return new String(DatatypeConverter.parseBase64Binary(str));
  }

  /**
   * Supprime les caracteres unicodes de la liste CtrlChar.
   *
   * @param texte texte a parser
   * @return le texte avec les caracteres unicodes supprimes
   */
  public static String deleteWindowsChar(String texte) {
    String result = texte;
    if (!texte.trim().isEmpty()) {
      for (CtrlChar ctrlChar : CtrlChar.values()) {
        if (!ctrlChar.equals(CtrlChar.ZERO_WIDTH_SPACE)) {
          result = Pattern.compile(ctrlChar.getValue(), Pattern.CASE_INSENSITIVE).matcher(texte).replaceAll(" ");
        } else {
          result = Pattern.compile(ctrlChar.getValue(), Pattern.CASE_INSENSITIVE).matcher(texte).replaceAll("");
        }
      }
    }
    return result;
  }

  /**
   * Decode une chaine de caractere encodee en hexadecimal.
   * 
   * @param s
   *          chaine de caractere en hexa.
   * @return la chaine decodee.
   */
  public static String hexaDecode(String s) {
    byte[] b = new byte[s.length() / 2];
    for (int i = 0; i < b.length; i++) {
      int index = i * 2;
      int v = Integer.parseInt(s.substring(index, index + 2), 16);
      b[i] = (byte) v;
    }
    return new String(b);
  }

  /**
   * Encode une chaine de caracteres en hexa.
   * 
   * @param str
   *          chaine a code.
   * @return la chaine transformee en hexa.
   */
  public static String hexaEncode(String str) {
    char[] chars = str.toCharArray();
    StringBuilder strBuilder = new StringBuilder();
    for (int i = 0; i < chars.length; i++) {
      strBuilder.append(Integer.toHexString(chars[i]));
    }
    return strBuilder.toString();

  }

  /**
   * This method ensures that the output String has only valid XML unicode
   * characters as specified by the XML 1.0 standard. For reference, please see
   * <a href="http://www.w3.org/TR/2000/REC-xml-20001006#NT-Char">the
   * standard</a>. This method will return an empty String if the input is null
   * or empty.
   * 
   * @param in
   *          The String whose non-valid characters we want to remove.
   * @return The in String, stripped of non-valid characters.
   */
  public static String stripNonValidXMLCharacters(final String in) {
    // Used to hold the output.
    StringBuilder out = new StringBuilder();
    // Used to reference the current character.
    char current;

    // vacancy test.
    if (in == null || ("".equals(in))) {
      return "";
    }
    for (int i = 0; i < in.length(); i++) {
      // NOTE: No IndexOutOfBoundsException caught here;
      // it should not happen.
      current = in.charAt(i);
      if ((current == 0x9)
          || (current == 0xA)
          || (current == 0xD)
          || ((current >= 0x20) && (current <= 0xD7FF))
          || ((current >= 0xE000) && (current <= 0xFFFD))
          || ((current >= 0x10000) && (current <= 0x10FFFF))) {
        out.append(current);
      } else {
        // on remplace le caractere invalide par un espace
        out.append(" ");
      }
    }
    return out.toString();
  }

}
