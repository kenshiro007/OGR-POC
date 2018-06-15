package fr.demo.metier.dto;

import fr.demo.metier.model.core.*;
import org.hibernate.validator.constraints.Length;
import java.io.Serializable;
import java.util.Date;

public class ReclamationDto implements IdObject, VersionObject, Auditable, Serializable, InfoConnexionsObject {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private AuditObject audit;

    private InfoConnexions infoConnexions;

    private Long idReclamation;


    private Date dateModification;

    private Date dateFinPublication;

    @Length(max = 100)
    private String objetReclamation;

// TODO : add pattern validation depending of the business scenario
    //@Pattern(regexp = "((http|https)://(.+)\\.linkedin\\.com(.+))|((http|https)://linkd\\.in\\/(.+))", message = "{fr.demo.validator.constraints.url.message}", groups = ReclamationInterlocuteursTraitement.class)
    private String lienLinkedin;

    //@Pattern(regexp = "(http|https)://(.+)\\.viadeo\\.com(.+)", message = "{fr.demo.validator.constraints.url.message}", groups = ReclamationInterlocuteursTraitement.class)
    private String lienViadeo;

    @Length(max = 256)
    private String url;

    private String description;
    
    private Long numeroVersion;


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

	public Date getDateModification() {
		return dateModification;
	}

	public void setDateModification(Date dateModification) {
		this.dateModification = dateModification;
	}

	public Date getDateFinPublication() {
		return dateFinPublication;
	}

	public void setDateFinPublication(Date dateFinPublication) {
		this.dateFinPublication = dateFinPublication;
	}

	public String getObjetReclamation() {
		return objetReclamation;
	}

	public void setObjetReclamation(String objetReclamation) {
		this.objetReclamation = objetReclamation;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


}


