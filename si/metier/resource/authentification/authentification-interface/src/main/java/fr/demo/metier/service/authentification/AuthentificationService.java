package fr.demo.metier.service.authentification;

import java.util.Date;
import java.util.Map;

import fr.demo.metier.dto.authentification.*;
import fr.demo.metier.exception.ValidationException;

  public interface AuthentificationService {

  UtilisateurPersonnelDto getAuthentificationComptePersonnel(AuthentificationDto authentification) throws ValidationException;

  UtilisateurGeneriqueDto getAuthentificationCompteGenerique(AuthentificationDto authentification) throws ValidationException;

  String generateToken(String numeroDossier);

  String generateToken(String numeroDossier, Date date);

  String generateToken(String numeroDossier, Date dateGeneration, Long duration, String parametres);

  boolean isTokenCadreValid(String token);

  /**
   * teste si le token passé est un nouveau (avec version) ou un ancien
   * @param token
   * @return {@link Boolean} true si c'est un nouveau token, false si non
     */
  Boolean isNewToken(String token);

  AuthentificationInfoDto getCadreAuthentificationInfoFromToken(String token);

  /**
   * 1. déchiffre le token 2.récupére le compte 3.vérifie que le compte est toujours disponible 4.vérifie la validité du token
   * 5. le/les paramètres "supplémentaire(s)" qui a/ont été passé dans le token
   *
   * @param token
   * @return {@link String}
     */
  String getParamsFromToken(String token);

  Map<String, String> validateUtilisateurModification(AuthentificationModificationDto authentificationModificationDto, String scenario, String numeroDossier);

  boolean isFormerIdenticalPasswordCadre(String numeroDossier, String password);

}
