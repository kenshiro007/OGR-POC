package fr.demo.metier.model.utils;

import org.apache.commons.lang.StringEscapeUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.html.HTML;

public class TexteBrutUtil {

  // caractère saut de ligne
  private static final String SAUT_DE_LIGNE = "\n";
  
  private static final Pattern BR_PATTERN = Pattern.compile("(<br( )*(\\/)?>)|(<p>)", Pattern.CASE_INSENSITIVE);
  
  private static final Pattern SLASH_UL_OL_PATTERN = Pattern.compile("\\s*</li>( )*(</ol>|</ul>)", Pattern.CASE_INSENSITIVE);

  private static final Pattern UL_OL_PATTERN = Pattern.compile("\\s*(<ol>|<ul>)<li>", Pattern.CASE_INSENSITIVE);
  
  private static final Pattern LI_PATTERN = Pattern.compile("</li>", Pattern.CASE_INSENSITIVE);
  
  private static final Pattern OTHER_HTML_PATTERN = Pattern.compile("<[^>]+>");
  
  private static final Pattern SPACE_PATTERN = Pattern.compile("&nbsp;|\n|\r");

  private static final Pattern COLON_PATTERN = Pattern.compile(":\\.");
  
  private static final Pattern LT_PATTERN = Pattern.compile("&lt;", Pattern.CASE_INSENSITIVE);

  private static final Pattern GT_PATTERN = Pattern.compile("&gt;", Pattern.CASE_INSENSITIVE);
  
  private static final Pattern AMP_PATTERN = Pattern.compile("&amp;", Pattern.CASE_INSENSITIVE);
  
  private static final Pattern EURO_PATTERN = Pattern.compile("&euro;", Pattern.CASE_INSENSITIVE);
  
  private static final Pattern SPECIAL_COMMA_PATTERN = Pattern.compile("&#61444;", Pattern.CASE_INSENSITIVE);
  
  private static final Pattern SHY_PATTERN = Pattern.compile("&shy;", Pattern.CASE_INSENSITIVE);
  
  private static final Pattern BACKSPACE_PATTERN = Pattern.compile("\u0008");
  
  private static final Pattern NO_BREAK_SPACE_PATTERN = Pattern.compile("\u00A0");

  private static final Pattern SOH_CHAR_PATTERN = Pattern.compile("\u0001");

  private static final Pattern SINGLE_QUOTE_PATTERN = Pattern.compile("\u0019");

  private static final Pattern MULTI_SPACES_PATTERN = Pattern.compile("( )+");

  private TexteBrutUtil() {
  }

  public static String getTexteBrut(String texteHtml) {
    if (texteHtml == null || texteHtml.trim().isEmpty()) {
      return "";
    }
    String texteRiche = TextEncodingUtils.deleteWindowsChar(texteHtml);
    texteRiche = filtreHtmlAndSpecials(texteRiche);

    texteRiche = texteRiche.trim(); // pour retirer les espaces et \n en début et fin de chaine

    return StringEscapeUtils.unescapeHtml(texteRiche);
  }

  public static String getTexteBrutDecompte(String texteHtml) {
    if (texteHtml == null || texteHtml.trim().isEmpty()) {
      return "";
    }
    String texteRiche = TextEncodingUtils.deleteWindowsChar(texteHtml);
    texteRiche = filtreHtmlAndSpecialsDecompte(texteRiche);
    texteRiche = texteRiche.trim();
    return StringEscapeUtils.unescapeHtml(texteRiche);
  }

    /**
     * Cette méthode permet de convertir du texte HTML en Texte brut formaté
     * @param texte
     * @return
     */
  private static String filtreHtmlAndSpecials(String texte) {
    String result;

    // on enlève les caractères &nbsp; qui sont générés par Internet
    // explorer &nbsp; par le caractère espace
    result = SPACE_PATTERN.matcher(texte).replaceAll(" ");

    // on remplace les balises <br>, <br/>, <p> par le caractère CHR(10)
    // (valable aussi pour <BR>, <BR/>, <P>)
    result = BR_PATTERN.matcher(result).replaceAll(SAUT_DE_LIGNE);

    // on remplace les suites de balises </li></ol> et </li></ul> par un . et
    // un saut de ligne
    result = SLASH_UL_OL_PATTERN.matcher(result).replaceAll("." + SAUT_DE_LIGNE);

    // on enlève les suites de balises <ol><li> et <ul><li>
    result = UL_OL_PATTERN.matcher(result).replaceAll(SAUT_DE_LIGNE);

    // on remplace les <li> par des " ;"
    result = LI_PATTERN.matcher(result).replaceAll("; ");

    // on enlève les balises html restante
    result = OTHER_HTML_PATTERN.matcher(result).replaceAll(" ");

    // on remplace la suite de caractères :. par un saut de ligne
    result = COLON_PATTERN.matcher(result).replaceAll(":" + SAUT_DE_LIGNE);

    // on remplace les codes &lt; par < et &gt; par >
    result = LT_PATTERN.matcher(result).replaceAll("<");
    result = GT_PATTERN.matcher(result).replaceAll(">");
    // on remplace les codes &amp; par &
    result = AMP_PATTERN.matcher(result).replaceAll("&");
    // on remplace les codes &euro; par €
    result = EURO_PATTERN.matcher(result).replaceAll("\u20AC");

    // on remplace les codes &#61444; par une virgule ,
    result = SPECIAL_COMMA_PATTERN.matcher(result).replaceAll(",");
    // on remplace les codes &shy; par un trait d'union insécable ,
    result = SHY_PATTERN.matcher(result).replaceAll("-");

    // on remplace le caractere backspace \b par une chaine vide
    result = BACKSPACE_PATTERN.matcher(result).replaceAll("");

    // on remplacer NO-BREAK SPACE par un espace
    result = NO_BREAK_SPACE_PATTERN.matcher(result).replaceAll(" ");

    result = SOH_CHAR_PATTERN.matcher(result).replaceAll(" ");

    result = SINGLE_QUOTE_PATTERN.matcher(result).replaceAll("'");

    // on remplacer plusieurs espaces succesifs par un seule
    result = MULTI_SPACES_PATTERN.matcher(result).replaceAll(" ");

    return result;
  }

    /**
     * Cette méthode permet de convertir du HTML en texte pour décompte
     * @param texte
     * @return
     */
    private static String filtreHtmlAndSpecialsDecompte(String texte) {
        String result;

        //leamsi
        //result = DBL_SAUT_DE_LIGNE.matcher(texte).replaceAll(" ");

        // on enlève les caractères &nbsp; qui sont générés par Internet
        // explorer &nbsp; par le caractère espace
        result = SPACE_PATTERN.matcher(texte).replaceAll("");

        // on remplace les balises <br>, <br/>, <p> par le caractère CHR(10)
        // (valable aussi pour <BR>, <BR/>, <P>)
        result = BR_PATTERN.matcher(result).replaceAll("");

        // on remplace les suites de balises </li></ol> et </li></ul> par un . et
        // un saut de ligne
        result = SLASH_UL_OL_PATTERN.matcher(result).replaceAll("");

        // on enlève les suites de balises <ol><li> et <ul><li>
        result = UL_OL_PATTERN.matcher(result).replaceAll("");

        // on remplace les <li> par des ""
        result = LI_PATTERN.matcher(result).replaceAll("");

        // on enlève les balises html restante
        result = OTHER_HTML_PATTERN.matcher(result).replaceAll("");

        // on remplace la suite de caractères :. par un saut de ligne
        result = COLON_PATTERN.matcher(result).replaceAll(":" + SAUT_DE_LIGNE);

        // on remplace les codes &lt; par < et &gt; par >
        result = LT_PATTERN.matcher(result).replaceAll("<");
        result = GT_PATTERN.matcher(result).replaceAll(">");
        // on remplace les codes &amp; par &
        result = AMP_PATTERN.matcher(result).replaceAll("&");
        // on remplace les codes &euro; par €
        result = EURO_PATTERN.matcher(result).replaceAll("\u20AC");

        // on remplace les codes &#61444; par une virgule ,
        result = SPECIAL_COMMA_PATTERN.matcher(result).replaceAll(",");
        // on remplace les codes &shy; par un trait d'union insécable ,
        result = SHY_PATTERN.matcher(result).replaceAll("-");

        // on remplace le caractere backspace \b par une chaine vide
        result = BACKSPACE_PATTERN.matcher(result).replaceAll("");

        // on remplacer NO-BREAK SPACE par un espace
        result = NO_BREAK_SPACE_PATTERN.matcher(result).replaceAll(" ");

        result = SOH_CHAR_PATTERN.matcher(result).replaceAll(" ");

        result = SINGLE_QUOTE_PATTERN.matcher(result).replaceAll("'");

        // on remplacer plusieurs espaces succesifs par un seule
        result = MULTI_SPACES_PATTERN.matcher(result).replaceAll("");

        return result;
    }
  
  public static String removeHtmlTags(String texte) {
	  StringBuilder result = new StringBuilder();
	  int idx = 0;
	  
	  Matcher htmlTags = Pattern.compile("(<) *(/)? *(?<tagName>[a-zA-Z]+)( +[^>]*)?(/)?(>)").matcher(texte);
	  while (htmlTags.find()) {
		  String tagName = htmlTags.group("tagName");
		  if (HTML.getTag(tagName) != null) {
			  result.append(texte.substring(idx, htmlTags.start()));
			  idx = htmlTags.end();
		  }
	  }
	  if (idx < texte.length()) {
		  result.append(texte.substring(idx));
	  }
	  
	  return result.toString();
  }
  
  
}
