package ch.fer.epost.application.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the KeyReference entity.
 */
public class KeyReferenceDTO implements Serializable {

    private Long id;

    @NotNull
    private String internalKey;

    @NotNull
    private String ePostKeyName;

    @NotNull
    private String ePostKeyValue;

    private Long internalKeyId;

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

    public Long getInternalKeyId() {
        return internalKeyId;
    }

    public void setInternalKeyId(Long dataReferenceId) {
        this.internalKeyId = dataReferenceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        KeyReferenceDTO keyReferenceDTO = (KeyReferenceDTO) o;
        if(keyReferenceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), keyReferenceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "KeyReferenceDTO{" +
            "id=" + getId() +
            ", internalKey='" + getInternalKey() + "'" +
            ", ePostKeyName='" + getePostKeyName() + "'" +
            ", ePostKeyValue='" + getePostKeyValue() + "'" +
            "}";
    }
}
