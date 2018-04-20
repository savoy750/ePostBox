package ch.fer.epost.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A RejectedRegistration.
 */
@Entity
@Table(name = "rejected_registration")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "rejectedregistration")
public class RejectedRegistration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "e_post_key_name", nullable = false)
    private String ePostKeyName;

    @NotNull
    @Column(name = "e_post_key_value", nullable = false)
    private String ePostKeyValue;

    @Column(name = "no_avs")
    private String noAVS;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "date_de_naissance")
    private Instant dateDeNaissance;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getePostKeyName() {
        return ePostKeyName;
    }

    public RejectedRegistration ePostKeyName(String ePostKeyName) {
        this.ePostKeyName = ePostKeyName;
        return this;
    }

    public void setePostKeyName(String ePostKeyName) {
        this.ePostKeyName = ePostKeyName;
    }

    public String getePostKeyValue() {
        return ePostKeyValue;
    }

    public RejectedRegistration ePostKeyValue(String ePostKeyValue) {
        this.ePostKeyValue = ePostKeyValue;
        return this;
    }

    public void setePostKeyValue(String ePostKeyValue) {
        this.ePostKeyValue = ePostKeyValue;
    }

    public String getNoAVS() {
        return noAVS;
    }

    public RejectedRegistration noAVS(String noAVS) {
        this.noAVS = noAVS;
        return this;
    }

    public void setNoAVS(String noAVS) {
        this.noAVS = noAVS;
    }

    public String getNom() {
        return nom;
    }

    public RejectedRegistration nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public RejectedRegistration prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Instant getDateDeNaissance() {
        return dateDeNaissance;
    }

    public RejectedRegistration dateDeNaissance(Instant dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
        return this;
    }

    public void setDateDeNaissance(Instant dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RejectedRegistration rejectedRegistration = (RejectedRegistration) o;
        if (rejectedRegistration.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rejectedRegistration.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RejectedRegistration{" +
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
