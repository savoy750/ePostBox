package ch.fer.epost.application.service.mapper;

import ch.fer.epost.application.domain.*;
import ch.fer.epost.application.service.dto.DataReferenceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DataReference and its DTO DataReferenceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DataReferenceMapper extends EntityMapper<DataReferenceDTO, DataReference> {



    default DataReference fromId(Long id) {
        if (id == null) {
            return null;
        }
        DataReference dataReference = new DataReference();
        dataReference.setId(id);
        return dataReference;
    }
}
