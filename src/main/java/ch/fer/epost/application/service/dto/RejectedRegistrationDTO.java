package ch.fer.epost.application.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the RejectedRegistration entity.
 */
public class RejectedRegistrationDTO implements Serializable {

    private Long id;

    @NotNull
    private String ePostKeyName;

    @NotNull
    private String ePostKeyValue;

    private String noAVS;

    private String nom;

    private String prenom;

    private Instant dateDeNaissance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getePostKeyName() {
        return ePostKeyName;
    }

    public void setePostKeyName(String ePostKeyName) {
        this.ePostKeyName = ePostKeyName;
    }

    public String getePostKeyValue() {
        return ePostKeyValue;
    }

    public void setePostKeyValue(String ePostKeyValue) {
        this.ePostKeyValue = ePostKeyValue;
    }

    public String getNoAVS() {
        return noAVS;
    }

    public void setNoAVS(String noAVS) {
        this.noAVS = noAVS;
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

    public Instant getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(Instant dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RejectedRegistrationDTO rejectedRegistrationDTO = (RejectedRegistrationDTO) o;
        if(rejectedRegistrationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rejectedRegistrationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RejectedRegistrationDTO{" +
            "id=" + getId() +
            ", ePostKeyName='" + getePostKeyName() + "'" +
            ", ePostKeyValue='" + getePostKeyValue() + "'" +
            ", noAVS='" + getNoAVS() + "'" +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", dateDeNaissance='" + getDateDeNaissance() + "'" +
            "}";
    }
}
