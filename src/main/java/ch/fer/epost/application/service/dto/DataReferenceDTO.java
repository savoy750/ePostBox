package ch.fer.epost.application.service.dto;


import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the DataReference entity.
 */
public class DataReferenceDTO implements Serializable {

    private Long id;

    private String internalKey;

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

    public String getInternalKey() {
        return internalKey;
    }

    public void setInternalKey(String internalKey) {
        this.internalKey = internalKey;
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

        DataReferenceDTO dataReferenceDTO = (DataReferenceDTO) o;
        if(dataReferenceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dataReferenceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DataReferenceDTO{" +
            "id=" + getId() +
            ", internalKey='" + getInternalKey() + "'" +
            ", noAVS='" + getNoAVS() + "'" +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", dateDeNaissance='" + getDateDeNaissance() + "'" +
            "}";
    }
}
