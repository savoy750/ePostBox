package ch.fer.epost.application.service;

import ch.fer.epost.application.service.dto.RejectedRegistrationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing RejectedRegistration.
 */
public interface RejectedRegistrationService {

    /**
     * Save a rejectedRegistration.
     *
     * @param rejectedRegistrationDTO the entity to save
     * @return the persisted entity
     */
    RejectedRegistrationDTO save(RejectedRegistrationDTO rejectedRegistrationDTO);

    /**
     * Get all the rejectedRegistrations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<RejectedRegistrationDTO> findAll(Pageable pageable);

    /**
     * Get the "id" rejectedRegistration.
     *
     * @param id the id of the entity
     * @return the entity
     */
    RejectedRegistrationDTO findOne(Long id);

    /**
     * Delete the "id" rejectedRegistration.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the rejectedRegistration corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<RejectedRegistrationDTO> search(String query, Pageable pageable);
}
