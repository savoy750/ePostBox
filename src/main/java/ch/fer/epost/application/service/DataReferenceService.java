package ch.fer.epost.application.service;

import ch.fer.epost.application.service.dto.DataReferenceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing DataReference.
 */
public interface DataReferenceService {

    /**
     * Save a dataReference.
     *
     * @param dataReferenceDTO the entity to save
     * @return the persisted entity
     */
    DataReferenceDTO save(DataReferenceDTO dataReferenceDTO);

    /**
     * Get all the dataReferences.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DataReferenceDTO> findAll(Pageable pageable);

    /**
     * Get the "id" dataReference.
     *
     * @param id the id of the entity
     * @return the entity
     */
    DataReferenceDTO findOne(Long id);

    /**
     * Delete the "id" dataReference.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the dataReference corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DataReferenceDTO> search(String query, Pageable pageable);
}
