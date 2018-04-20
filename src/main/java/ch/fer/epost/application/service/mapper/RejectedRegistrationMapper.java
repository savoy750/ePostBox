package ch.fer.epost.application.service.mapper;

import ch.fer.epost.application.domain.*;
import ch.fer.epost.application.service.dto.RejectedRegistrationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RejectedRegistration and its DTO RejectedRegistrationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RejectedRegistrationMapper extends EntityMapper<RejectedRegistrationDTO, RejectedRegistration> {



    default RejectedRegistration fromId(Long id) {
        if (id == null) {
            return null;
        }
        RejectedRegistration rejectedRegistration = new RejectedRegistration();
        rejectedRegistration.setId(id);
        return rejectedRegistration;
    }
}
