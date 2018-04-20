package ch.fer.epost.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

import ch.fer.epost.application.domain.enumeration.Status;

/**
 * A DocumentsSend.
 */
@Entity
@Table(name = "documents_send")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "documentssend")
public class DocumentsSend implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "correlation_id")
    private String correlationId;

    @Column(name = "document_type")
    private Integer documentType;

    @Column(name = "tag")
    private String tag;

    @Column(name = "internal_key")
    private String internalKey;

    @Column(name = "message")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ManyToOne
    private KeyReference internalKey;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public DocumentsSend title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public DocumentsSend correlationId(String correlationId) {
        this.correlationId = correlationId;
        return this;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public Integer getDocumentType() {
        return documentType;
    }

    public DocumentsSend documentType(Integer documentType) {
        this.documentType = documentType;
        return this;
    }

    public void setDocumentType(Integer documentType) {
        this.documentType = documentType;
    }

    public String getTag() {
        return tag;
    }

    public DocumentsSend tag(String tag) {
        this.tag = tag;
        return this;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getInternalKey() {
        return internalKey;
    }

    public DocumentsSend internalKey(String internalKey) {
        this.internalKey = internalKey;
        return this;
    }

    public void setInternalKey(String internalKey) {
        this.internalKey = internalKey;
    }

    public String getMessage() {
        return message;
    }

    public DocumentsSend message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public DocumentsSend status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public KeyReference getInternalKey() {
        return internalKey;
    }

    public DocumentsSend internalKey(KeyReference keyReference) {
        this.internalKey = keyReference;
        return this;
    }

    public void setInternalKey(KeyReference keyReference) {
        this.internalKey = keyReference;
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
        DocumentsSend documentsSend = (DocumentsSend) o;
        if (documentsSend.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), documentsSend.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DocumentsSend{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", correlationId='" + getCorrelationId() + "'" +
            ", documentType=" + getDocumentType() +
            ", tag='" + getTag() + "'" +
            ", internalKey='" + getInternalKey() + "'" +
            ", message='" + getMessage() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
