package ch.fer.epost.application.service;

import ch.fer.epost.application.service.dto.KeyReferenceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing KeyReference.
 */
public interface KeyReferenceService {

    /**
     * Save a keyReference.
     *
     * @param keyReferenceDTO the entity to save
     * @return the persisted entity
     */
    KeyReferenceDTO save(KeyReferenceDTO keyReferenceDTO);

    /**
     * Get all the keyReferences.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<KeyReferenceDTO> findAll(Pageable pageable);

    /**
     * Get the "id" keyReference.
     *
     * @param id the id of the entity
     * @return the entity
     */
    KeyReferenceDTO findOne(Long id);

    /**
     * Delete the "id" keyReference.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the keyReference corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<KeyReferenceDTO> search(String query, Pageable pageable);
}
