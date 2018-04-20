package ch.fer.epost.application.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import ch.fer.epost.application.domain.enumeration.Status;

/**
 * A DTO for the DocumentsSend entity.
 */
public class DocumentsSendDTO implements Serializable {

    private Long id;

    private String title;

    private String correlationId;

    private Integer documentType;

    private String tag;

    private String internalKey;

    private String message;

    private Status status;

    private Long internalKeyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public Integer getDocumentType() {
        return documentType;
    }

    public void setDocumentType(Integer documentType) {
        this.documentType = documentType;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getInternalKey() {
        return internalKey;
    }

    public void setInternalKey(String internalKey) {
        this.internalKey = internalKey;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getInternalKeyId() {
        return internalKeyId;
    }

    public void setInternalKeyId(Long keyReferenceId) {
        this.internalKeyId = keyReferenceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DocumentsSendDTO documentsSendDTO = (DocumentsSendDTO) o;
        if(documentsSendDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), documentsSendDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DocumentsSendDTO{" +
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
