package fr.demo.metier.model.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe de manipulation de chaines. LEGACY
 */
public class StringUtils {

  /**
   * Vecteur de correspondance entre accent / sans accent *
   */
  private static final Map<Integer, String> MAP = new HashMap<Integer, String>() {{
    put(192, "A");
    put(193, "A");
    put(194, "A");
    put(195, "A");
    put(196, "A");
    put(197, "A");
    put(198, "AE");
    put(199, "C");
    put(200, "E");
    put(201, "E");
    put(202, "E");
    put(203, "E");
    put(204, "I");
    put(205, "I");
    put(206, "I");
    put(207, "I");
    put(208, "D");
    put(209, "N");
    put(210, "O");
    put(211, "O");
    put(212, "O");
    put(213, "O");
    put(214, "O");
    put(215, "*");
    put(216, "O");
    put(217, "U");
    put(218, "U");
    put(219, "U");
    put(220, "U");
    put(221, "Y");
    put(222, "p");
    put(223, "B");
    put(224, "a");
    put(225, "a");
    put(226, "a");
    put(227, "a");
    put(228, "a");
    put(229, "a");
    put(230, "ae");
    put(231, "c");
    put(232, "e");
    put(233, "e");
    put(234, "e");
    put(235, "e");
    put(236, "i");
    put(237, "i");
    put(238, "i");
    put(239, "i");
    put(240, "d");
    put(241, "n");
    put(242, "o");
    put(243, "o");
    put(244, "o");
    put(245, "o");
    put(246, "o");
    put(247, "/");
    put(248, "o");
    put(249, "u");
    put(250, "u");
    put(251, "u");
    put(252, "u");
    put(253, "y");
    put(254, "P");
    put(255, "y");
    put(338, "OE");
    put(339, "oe");
  }};

  private StringUtils() {
    super();
  }

  /**
   * @return the map.
   */
  public static Map<Integer, String> getMap() {
    return MAP;
  }

  /**
   * Transforme une chaine pouvant contenir des accents dans une version sans
   * accent
   *
   * @param chaine Chaine a convertir sans accent
   * @return Chaine dont les accents ont été supprimés
   */
  public static String toStandardString(String chaine) {
    StringBuilder result = new StringBuilder("");
    char[] tab = new char[1];
    // On traite caractère par caractère toute la chaine. Si on tombe sur un des 4 caractères spéciaux Æ, æ, Œ ou œ, on remplace par deux charactères, ce qui brise la correspondance entre les tailles
    // des chaines en entrée et en sortie. C'est pourquoi on décrit les points de correspondance entre l'entrée et la sortie dans les vecteurs inputSubStrings et outputSubStrings afin que la méthode
    // appelante puisse faire des comparaisons si nécessaire entre la chaine passée en entrée celle en sortie, comme par exemple lorsqu'on souhaite faire du highlighting
    for (int bcl = 0; bcl < chaine.length(); bcl++) {
      int carVal = chaine.charAt(bcl);
      String val = getMap().get(carVal);
      if (val != null) {
        result.append(val);
      } else {
        tab[0] = chaine.charAt(bcl);
        result.append(tab);
      }
    }
    return result.toString();
  }

}
