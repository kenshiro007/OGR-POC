package fr.demo.metier.dto.authentification;

import com.wordnik.swagger.annotations.ApiModelProperty;
import fr.demo.metier.model.core.AuditObject;
import fr.demo.metier.model.core.Auditable;
import fr.demo.metier.model.core.VersionObject;
import fr.demo.metier.validator.authentification.*;
import fr.demo.metier.validator.constraints.authentification.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.AssertTrue;
import java.io.Serializable;

public class AuthentificationModificationDto extends AuthentificationInfoDto implements VersionObject, Serializable, Auditable {

    private static final long serialVersionUID = 1L;

    protected AuditObject audit;

    private Long numeroVersion;

    private String ancienMotDePasse;

    @NotBlank(groups = {AuthentificationBasique.class, AuthentificationModification.class, AuthentificationModificationSecurise.class})
    @ApiModelProperty(value = "Mot de passe", required = true)

    @AuMoinsUnChiffreConstraint(groups = {AuthentificationModification.class, AuthentificationModificationSecurise.class})
    @AuMoinsUneMajusculeConstraint(groups = {AuthentificationModification.class, AuthentificationModificationSecurise.class})
    @AuMoinsUneMinusculeConstraint(groups = {AuthentificationModification.class, AuthentificationModificationSecurise.class})
    @AuMoinsUnSpecialConstraint(groups = {AuthentificationModification.class, AuthentificationModificationSecurise.class})
    @PasswordSizeConstraint(groups = {AuthentificationModification.class, AuthentificationModificationSecurise.class})

    protected String motDePasse;

    protected String motDePasseSha512;

    protected String motDePasseSel;

    @NotBlank(groups = {AuthentificationValidationToken.class})
    @TokenValidConstraint(groups = {AuthentificationValidationToken.class})
    private String token;

    @AssertTrue(groups = {AuthentificationModification.class})
    private Boolean cguAcceptees;

    public AuthentificationModificationDto() {}

    public AuthentificationModificationDto(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    @Override
    public AuditObject getAudit() {
        return audit;
    }

    @Override
    public void setAudit(AuditObject audit) {
        this.audit = audit;
    }

    @Override
    public Long getNumeroVersion() {
        return numeroVersion;
    }

    @Override
    public void setNumeroVersion(Long numeroVersion) {
        this.numeroVersion = numeroVersion;
    }

    public String getAncienMotDePasse() {
        return ancienMotDePasse;
    }

    public void setAncienMotDePasse(String motDePasse) {
        this.ancienMotDePasse = motDePasse;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getMotDePasseSha512() {
        return motDePasseSha512;
    }

    public void setMotDePasseSha512(String motDePasseSha512) {
        this.motDePasseSha512 = motDePasseSha512;
    }

    public String getMotDePasseSel() {
        return motDePasseSel;
    }

    public void setMotDePasseSel(String motDePasseSel) {
        this.motDePasseSel = motDePasseSel;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getCguAcceptees() {
        return cguAcceptees;
    }

    public void setCguAcceptees(Boolean cguAcceptees) {
        this.cguAcceptees = cguAcceptees;
    }
}
