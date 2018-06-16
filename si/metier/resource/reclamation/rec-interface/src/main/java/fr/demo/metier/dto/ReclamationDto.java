package fr.demo.metier.dto;

import fr.demo.metier.model.core.*;
import org.hibernate.validator.constraints.Length;
import java.io.Serializable;

public class ReclamationDto implements IdObject, VersionObject, Auditable, Serializable, InfoConnexionsObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private AuditObject audit;

	private InfoConnexions infoConnexions;

	private Long idReclamation;

	@Length(max = 255)
	private String causeReclamation, description, reponse, url;

	private Long familleReclamation, typeReclamation, numeroVersion;
	
	private boolean fonde;

	// TODO : add pattern validation depending of the business scenario
	// @Pattern(regexp =
	// "((http|https)://(.+)\\.linkedin\\.com(.+))|((http|https)://linkd\\.in\\/(.+))",
	// message = "{fr.demo.validator.constraints.url.message}", groups =
	// ReclamationInterlocuteursTraitement.class)
	private String lienLinkedin;

	// @Pattern(regexp = "(http|https)://(.+)\\.viadeo\\.com(.+)", message =
	// "{fr.demo.validator.constraints.url.message}", groups =
	// ReclamationInterlocuteursTraitement.class)
	private String lienViadeo;

	public ReclamationDto() {
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
	public InfoConnexions getInfoConnexions() {
		return infoConnexions;
	}

	@Override
	public void setInfoConnexions(InfoConnexions infoConnexions) {
		this.infoConnexions = infoConnexions;
	}

	@Override
	public Long getId() {
		return idReclamation;
	}

	@Override
	public void setId(Long id) {
		this.idReclamation = id;

	}

	@Override
	public Long getNumeroVersion() {
		return numeroVersion;
	}

	@Override
	public void setNumeroVersion(Long numeroVersion) {
		this.numeroVersion = numeroVersion;

	}

	public Long getIdReclamation() {
		return idReclamation;
	}

	public void setIdReclamation(Long idReclamation) {
		this.idReclamation = idReclamation;
	}

	public String getCauseReclamation() {
		return causeReclamation;
	}

	public void setCauseReclamation(String causeReclamation) {
		this.causeReclamation = causeReclamation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getReponse() {
		return reponse;
	}

	public void setReponse(String reponse) {
		this.reponse = reponse;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getFamilleReclamation() {
		return familleReclamation;
	}

	public void setFamilleReclamation(Long familleReclamation) {
		this.familleReclamation = familleReclamation;
	}

	public Long getTypeReclamation() {
		return typeReclamation;
	}

	public void setTypeReclamation(Long typeReclamation) {
		this.typeReclamation = typeReclamation;
	}

	public String getLienLinkedin() {
		return lienLinkedin;
	}

	public void setLienLinkedin(String lienLinkedin) {
		this.lienLinkedin = lienLinkedin;
	}

	public String getLienViadeo() {
		return lienViadeo;
	}

	public void setLienViadeo(String lienViadeo) {
		this.lienViadeo = lienViadeo;
	}

	public boolean isFonde() {
		return fonde;
	}

	public void setFonde(boolean fonde) {
		this.fonde = fonde;
	}
	
	

}
