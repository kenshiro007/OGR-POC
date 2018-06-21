package fr.demo.metier.validator.authentification;

public class AuthentificationValidatorScenario {

  public static final String UPDATE_PASSWORD_TOKEN = "UPDATE_PASSWORD_TOKEN";

  public static final String CREATE_PASSWORD_CADRE = "CREATE_PASSWORD_CADRE";

  public static final String CREATE_PASSWORD_INTERLOCUTEUR = "CREATE_PASSWORD_INTERLOCUTEUR";

  public static final String CREATE_PASSWORD_UTILISATEUR_ADEP = "CREATE_PASSWORD_UTILISATEUR_ADEP";

  public static final String UPDATE_PASSWORD_CADRE = "UPDATE_PASSWORD_CADRE";

  public static final String UPDATE_PASSWORD_INTERLOCUTEUR = "UPDATE_PASSWORD_INTERLOCUTEUR";

  // retrocompatibilité AuthentificationTokenModificationValidator et AuthentificationForteModificationValidator
  public static final String UPDATE_PASSWORD = "UPDATE_PASSWORD";
  public static final String UPDATE_PASSWORD_SECURISE = "UPDATE_PASSWORD_SECURISE";

  public static Class<?>[] convert(String scenario) {
    switch (scenario) {
      // retrocompatibilité AuthentificationTokenModificationValidator et AuthentificationForteModificationValidator
      case UPDATE_PASSWORD_SECURISE:
        return new Class<?>[] {AuthentificationModificationSecurise.class};
      case UPDATE_PASSWORD:
        return new Class<?>[] {AuthentificationModification.class};
      // fin retrocompatibilité
      case UPDATE_PASSWORD_TOKEN:
        return new Class<?>[] {AuthentificationValidationToken.class};
      default:
        throw new IllegalArgumentException(scenario);
    }
  }

  private AuthentificationValidatorScenario(){
    // Nothing to do
  }
}
