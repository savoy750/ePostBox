package ch.fer.epost.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A KeyReference.
 */
@Entity
@Table(name = "key_reference")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "keyreference")
public class KeyReference implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "internal_key", nullable = false)
    private String internalKey;

    @NotNull
    @Column(name = "e_post_key_name", nullable = false)
    private String ePostKeyName;

    @NotNull
    @Column(name = "e_post_key_value", nullable = false)
    private String ePostKeyValue;

    @OneToOne
    @JoinColumn(unique = true)
    private DataReference internalKey;

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

    public KeyReference internalKey(String internalKey) {
        this.internalKey = internalKey;
        return this;
    }

    public void setInternalKey(String internalKey) {
        this.internalKey = internalKey;
    }

    public String getePostKeyName() {
        return ePostKeyName;
    }

    public KeyReference ePostKeyName(String ePostKeyName) {
        this.ePostKeyName = ePostKeyName;
        return this;
    }

    public void setePostKeyName(String ePostKeyName) {
        this.ePostKeyName = ePostKeyName;
    }

    public String getePostKeyValue() {
        return ePostKeyValue;
    }

    public KeyReference ePostKeyValue(String ePostKeyValue) {
        this.ePostKeyValue = ePostKeyValue;
        return this;
    }

    public void setePostKeyValue(String ePostKeyValue) {
        this.ePostKeyValue = ePostKeyValue;
    }

    public DataReference getInternalKey() {
        return internalKey;
    }

    public KeyReference internalKey(DataReference dataReference) {
        this.internalKey = dataReference;
        return this;
    }

    public void setInternalKey(DataReference dataReference) {
        this.internalKey = dataReference;
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
        KeyReference keyReference = (KeyReference) o;
        if (keyReference.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), keyReference.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "KeyReference{" +
            "id=" + getId() +
            ", internalKey='" + getInternalKey() + "'" +
            ", ePostKeyName='" + getePostKeyName() + "'" +
            ", ePostKeyValue='" + getePostKeyValue() + "'" +
            "}";
    }
}
