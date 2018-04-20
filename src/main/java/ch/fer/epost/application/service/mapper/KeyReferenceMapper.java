package ch.fer.epost.application.service.mapper;

import ch.fer.epost.application.domain.*;
import ch.fer.epost.application.service.dto.KeyReferenceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity KeyReference and its DTO KeyReferenceDTO.
 */
@Mapper(componentModel = "spring", uses = {DataReferenceMapper.class})
public interface KeyReferenceMapper extends EntityMapper<KeyReferenceDTO, KeyReference> {

    @Mapping(source = "internalKey.id", target = "internalKeyId")
    KeyReferenceDTO toDto(KeyReference keyReference);

    @Mapping(source = "internalKeyId", target = "internalKey")
    KeyReference toEntity(KeyReferenceDTO keyReferenceDTO);

    default KeyReference fromId(Long id) {
        if (id == null) {
            return null;
        }
        KeyReference keyReference = new KeyReference();
        keyReference.setId(id);
        return keyReference;
    }
}
