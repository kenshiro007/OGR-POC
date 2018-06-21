package fr.demo.metier.dto.authentification;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wordnik.swagger.annotations.ApiModelProperty;
import fr.demo.metier.validator.authentification.*;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;

public class AuthentificationDto {

  private static final Logger LOGGER = LoggerFactory.getLogger(AuthentificationDto.class);

  public enum EnumTypeIdentifiant {

    ID("id"), NUMERO_COMPTE("numéro de compte"), EMAIL("email"), LOGIN("login annuaire");

    protected String label;

    EnumTypeIdentifiant(String label) {
      this.label = label;
    }

    public String getLabel() {
      return label;
    }

    @JsonValue
    public String jsonValue() {
      return this.name();
    }

    @JsonCreator
    public static EnumTypeIdentifiant fromValue(String code) {
      for(EnumTypeIdentifiant e : values()) {
        if (code.equals(e.name())) {
          return e;
        }
      }
      LOGGER.debug("Code : [" + code + "] inconnu dans l'enum 'EnumTypeIdentifiant'.");
      return null;
    }

  }

  @NotNull(groups = AuthentificationBasique.class)
  @ApiModelProperty(value = "Type d'identifiant", allowableValues = "id, numeroCompte, email, login", required = true)
  private EnumTypeIdentifiant typeAuthentifiant;

  @NotNull(groups = AuthentificationId.class)
  @ApiModelProperty(value = "id", notes = "identifiant 'id de compte'")
  private Long idCompte;

  @NotBlank(groups = AuthentificationNumeroCompte.class)
  @ApiModelProperty(value = "numeroCompte", notes = "identifiant 'numéro de compte'")
  private String numeroCompte;

  @NotBlank(groups = AuthentificationModification.class)
  @ApiModelProperty(value = "Mot de passe", required = true)
  protected String motDePasse;

  @NotBlank(groups = AuthentificationLogin.class)
  @ApiModelProperty(value = "login", notes = "identifiant 'login annuaire'")
  private String login;

  public Long getId() {
    return idCompte;
  }

  public void setId(Long idCompte) {
    this.idCompte = idCompte;
  }

  public EnumTypeIdentifiant getTypeAuthentifiant() {
    return typeAuthentifiant;
  }

  public void setTypeAuthentifiant(EnumTypeIdentifiant typeAuthentifiant) {
    this.typeAuthentifiant = typeAuthentifiant;
  }

  public String getNumeroCompte() {
    return numeroCompte;
  }

  public void setNumeroCompte(String numeroCompte) {
    this.numeroCompte = numeroCompte;
  }

  public String getMotDePasse() {
    return motDePasse;
  }

  public void setMotDePasse(String motDePasse) {
    this.motDePasse = motDePasse;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

}
