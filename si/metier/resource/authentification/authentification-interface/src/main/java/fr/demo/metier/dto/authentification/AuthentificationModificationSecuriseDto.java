package fr.demo.metier.dto.authentification;

import com.wordnik.swagger.annotations.ApiModelProperty;
import fr.demo.metier.validator.authentification.AuthentificationModificationSecurise;
import org.hibernate.validator.constraints.NotBlank;

public class AuthentificationModificationSecuriseDto extends AuthentificationModificationDto {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "Ancien mot de passe", required = true, notes = "Utilisé pour valider la modification")
  @NotBlank(groups = AuthentificationModificationSecurise.class)

  private String ancienMotDePasse;

  public AuthentificationModificationSecuriseDto() {
  }

  public AuthentificationModificationSecuriseDto(AuthentificationModificationDto src){
    this.setAncienMotDePasse(src.getAncienMotDePasse());
    this.setMotDePasse(src.getMotDePasse());
    this.setAudit(src.getAudit());
    this.setCguAcceptees(src.getCguAcceptees());
    this.setNumeroVersion(src.getNumeroVersion());
    this.setToken(src.getToken());
  }

  public String getAncienMotDePasse() {
    return ancienMotDePasse;
  }

  public void setAncienMotDePasse(String motDePasse) {
    this.ancienMotDePasse = motDePasse;
  }

}
