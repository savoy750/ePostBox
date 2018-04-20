package ch.fer.epost.application.service;

import ch.fer.epost.application.service.dto.DocumentsSendDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing DocumentsSend.
 */
public interface DocumentsSendService {

    /**
     * Save a documentsSend.
     *
     * @param documentsSendDTO the entity to save
     * @return the persisted entity
     */
    DocumentsSendDTO save(DocumentsSendDTO documentsSendDTO);

    /**
     * Get all the documentsSends.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DocumentsSendDTO> findAll(Pageable pageable);

    /**
     * Get the "id" documentsSend.
     *
     * @param id the id of the entity
     * @return the entity
     */
    DocumentsSendDTO findOne(Long id);

    /**
     * Delete the "id" documentsSend.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the documentsSend corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DocumentsSendDTO> search(String query, Pageable pageable);
}
