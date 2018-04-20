package ch.fer.epost.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DataReference.
 */
@Entity
@Table(name = "data_reference")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "datareference")
public class DataReference implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "internal_key")
    private String internalKey;

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

    public String getInternalKey() {
        return internalKey;
    }

    public DataReference internalKey(String internalKey) {
        this.internalKey = internalKey;
        return this;
    }

    public void setInternalKey(String internalKey) {
        this.internalKey = internalKey;
    }

    public String getNoAVS() {
        return noAVS;
    }

    public DataReference noAVS(String noAVS) {
        this.noAVS = noAVS;
        return this;
    }

    public void setNoAVS(String noAVS) {
        this.noAVS = noAVS;
    }

    public String getNom() {
        return nom;
    }

    public DataReference nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public DataReference prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Instant getDateDeNaissance() {
        return dateDeNaissance;
    }

    public DataReference dateDeNaissance(Instant dateDeNaissance) {
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
        DataReference dataReference = (DataReference) o;
        if (dataReference.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dataReference.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DataReference{" +
            "id=" + getId() +
            ", internalKey='" + getInternalKey() + "'" +
            ", noAVS='" + getNoAVS() + "'" +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", dateDeNaissance='" + getDateDeNaissance() + "'" +
            "}";
    }
}
