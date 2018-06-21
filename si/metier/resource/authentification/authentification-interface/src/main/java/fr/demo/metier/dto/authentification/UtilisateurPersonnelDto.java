package fr.demo.metier.dto.authentification;

import fr.demo.metier.model.core.IdObject;

import java.io.Serializable;

/**
 * Created on 19/01/2015.
 */
public class UtilisateurPersonnelDto implements IdObject, Serializable {

    private static final long serialVersionUID = 1L;

    private Long idPersonnel;

    private String numeroRH;

    private String nom;

    private String prenom;

    private String loginAnnuaire;

    private String adresseEmail;

    @Override
    public Long getId() {
        return this.getIdPersonnel();
    }

    @Override
    public void setId(Long idPersonnel) {
        this.idPersonnel = idPersonnel;
    }

    public Long getIdPersonnel() {
        return idPersonnel;
    }

    public void setIdPersonnel(Long idPersonnel) {
        this.idPersonnel = idPersonnel;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getLoginAnnuaire() {
        return loginAnnuaire;
    }

    public void setLoginAnnuaire(String loginAnnuaire) {
        this.loginAnnuaire = loginAnnuaire;
    }

    public String getAdresseEmail() {
        return adresseEmail;
    }

    public void setAdresseEmail(String adresseEmail) {
        this.adresseEmail = adresseEmail;
    }

    public String getNumeroRH() {
        return numeroRH;
    }

    public void setNumeroRH(String numeroRH) {
        this.numeroRH = numeroRH;
    }
}
