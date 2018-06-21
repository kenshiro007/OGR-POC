package fr.demo.metier.utils;

import fr.BlowfishJ.BlowfishEasy;
import org.apache.commons.lang.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BlowFishUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlowFishUtils.class);
    public static final String TOKEN_PARAMS_SEPARATOR = ":";
    public static final String TOKEN_BODY_SIGNATURE_SEPARATOR = ".";
    public static final String TOKEN_SEL = "XXX";

    private BlowfishEasy blowfishEasy;

    private Sha512Utils sha512Utils;

    private SimpleDateFormat formatter;

    private SimpleDateFormat tokenWithVersionDateFormatter;

    public BlowFishUtils() {
        blowfishEasy = new BlowfishEasy("3ca4a416c6444954e07292e78cede13b");
        formatter = new SimpleDateFormat("ddMMyyyyHHmm");
        tokenWithVersionDateFormatter = new SimpleDateFormat("ddMMyyyyHHmmss");
    }

    /**
     * Encrypte une chaine de caractere avec l'algorithme BlowFish
     *
     * @param message message a encrypter
     * @return le message encrypté
     */
    public String encrypt(String message) {
        return blowfishEasy.encryptString(message);
    }

    /**
     * Decrypter une chaine de caractere avec l'algorithme BlowFish
     *
     * @param message message a décrypter
     * @return le message decrypté
     */
    public String decrypt(String message) {
        return blowfishEasy.decryptString(message);
    }

    /**
     * Genere un token encrypté
     *
     * @param numeroDossier numero de dossier du cadre ou de l'interlocuteur a generer
     * @return le token crypte contenant date + numero de dossier
     */
    public String generateToken(String numeroDossier) {
        Calendar currentDate = Calendar.getInstance();
        try {
            return URLEncoder.encode(encrypt(formatter.format(currentDate.getTime()) + numeroDossier), CharEncoding.UTF_8);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage(), e);
            return encrypt(formatter.format(currentDate.getTime()) + numeroDossier);
        }
    }

    /**
     * Genere un token encrypté
     *
     * @param numeroDossier numero de dossier du cadre ou de l'interlocuteur a generer
     * @param date date de génération du token
     * @return le token crypte contenant date + numero de dossier
     */

    public String generateToken(String numeroDossier, Date date) {
        try {
            return URLEncoder.encode(encrypt(formatter.format(date) + numeroDossier), CharEncoding.UTF_8);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage(), e);
            return encrypt(formatter.format(date) + numeroDossier);
        }
    }

    /**
     * Genere un token encrypté
     *
     * @param dateGeneration  la date de génération du token (premier parametre passé dans le token)
     * @param numeroDossier  le numéro du compte (deuxième parametre passé dans le token)
     * @param duration  la durée de validité du token (troisieme parametre passé dans le token)
     * @param parametre  contient la liste des/un paramètre à envoyer dans le token (dernier parametre passé dans le token)
     * @return le token crypte contenant date + numero de dossier + duration + le parametre supplémentaire
     */
    public String generateToken(Date dateGeneration, String numeroDossier, Long duration, String parametre) {
        String paramsToEncrypt = tokenWithVersionDateFormatter.format(dateGeneration) + TOKEN_PARAMS_SEPARATOR + numeroDossier + TOKEN_PARAMS_SEPARATOR + String.valueOf(duration.longValue()) + TOKEN_PARAMS_SEPARATOR + parametre;
        try {
            String numeroVersion = "1V";
            String tokenWithoutSignature = numeroVersion + URLEncoder.encode(encrypt(paramsToEncrypt), CharEncoding.UTF_8);
            return  tokenWithoutSignature + TOKEN_BODY_SIGNATURE_SEPARATOR + sha512Utils.encrypt(tokenWithoutSignature, TOKEN_SEL);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage(), e);
            return encrypt(paramsToEncrypt);
        }
    }

    /**
     * Valide le token
     *
     * @param lastConnection Date de derniere connexion entrant dans la validation du token
     * @param decryptedToken le token deja decrypte
     * @return vrai si le token est valide
     */
    public boolean isTokenValid(Date lastConnection, String decryptedToken) {
        return isTokenValidWithDuration(lastConnection, decryptedToken, 24);
    }

    /**
     * Valide le token provenant du mail de relance de publication
     * @param decryptedToken le token decrypte
     * @return vrai si le token est valide
     */
    public boolean isPublicationTokenValid(String decryptedToken) {
        return isTokenValidWithDuration(null, decryptedToken, 360);
    }

    /**
     * @param lastConnection Date de derniere connexion entrant dans la validation du token
     * @param decryptedToken le token deja decrypte
     * @param validityDurationInHours duree de la validite du token en heures
     * @return vrai si le token est valide
     */
    public boolean isTokenValidWithDuration(Date lastConnection, String decryptedToken, int validityDurationInHours) {
        String tokenDateStr = decryptedToken.substring(0, 12);
        Calendar currentDate = Calendar.getInstance();
        try {
            Date tokenDate = formatter.parse(tokenDateStr);
            Calendar tokenCalendarEndValidity = Calendar.getInstance();
            Calendar tokenCalendar = Calendar.getInstance();
            Calendar lastConnectionCalendar = Calendar.getInstance();
            tokenCalendarEndValidity.setTime(tokenDate);
            tokenCalendarEndValidity.add(Calendar.HOUR, validityDurationInHours);
            if (lastConnection != null) {
                lastConnectionCalendar.setTime(lastConnection);
                return tokenCalendarEndValidity.after(currentDate) && lastConnectionCalendar.before(tokenCalendar);
            } else {
                return tokenCalendarEndValidity.after(currentDate);
            }
        } catch (ParseException e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        }
    }

    /**
     * vérifie si le token est encore valable (selon la durée en seconde)
     * @param datedebut
     * @param validityDurationInSecond
     * @return @boolean true si le token est valide, false sinon
     */
    public boolean isTokenValidWithDurationInSecond(Date datedebut, int validityDurationInSecond) {
        Calendar currentDate = Calendar.getInstance();
        Calendar tokenCalendarEndValidity = Calendar.getInstance();
        tokenCalendarEndValidity.setTime(datedebut);
        tokenCalendarEndValidity.add(Calendar.SECOND, validityDurationInSecond);
        return tokenCalendarEndValidity.after(currentDate);
    }

    public SimpleDateFormat getTokenWithVersionDateFormatter() {
        return tokenWithVersionDateFormatter;
    }

    public void setTokenWithVersionDateFormatter(SimpleDateFormat tokenWithVersionDateFormatter) {
        this.tokenWithVersionDateFormatter = tokenWithVersionDateFormatter;
    }

    public Sha512Utils getSha512Utils() {
        return sha512Utils;
    }

    public void setSha512Utils(Sha512Utils sha512Utils) {
        this.sha512Utils = sha512Utils;
    }
}
