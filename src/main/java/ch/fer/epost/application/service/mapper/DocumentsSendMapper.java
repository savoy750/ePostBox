package ch.fer.epost.application.service.mapper;

import ch.fer.epost.application.domain.*;
import ch.fer.epost.application.service.dto.DocumentsSendDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DocumentsSend and its DTO DocumentsSendDTO.
 */
@Mapper(componentModel = "spring", uses = {KeyReferenceMapper.class})
public interface DocumentsSendMapper extends EntityMapper<DocumentsSendDTO, DocumentsSend> {

    @Mapping(source = "internalKey.id", target = "internalKeyId")
    DocumentsSendDTO toDto(DocumentsSend documentsSend);

    @Mapping(source = "internalKeyId", target = "internalKey")
    DocumentsSend toEntity(DocumentsSendDTO documentsSendDTO);

    default DocumentsSend fromId(Long id) {
        if (id == null) {
            return null;
        }
        DocumentsSend documentsSend = new DocumentsSend();
        documentsSend.setId(id);
        return documentsSend;
    }
}
