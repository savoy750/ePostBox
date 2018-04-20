package ch.fer.epost.application.service.impl;

import ch.fer.epost.application.service.KeyReferenceService;
import ch.fer.epost.application.domain.KeyReference;
import ch.fer.epost.application.repository.KeyReferenceRepository;
import ch.fer.epost.application.repository.search.KeyReferenceSearchRepository;
import ch.fer.epost.application.service.dto.KeyReferenceDTO;
import ch.fer.epost.application.service.mapper.KeyReferenceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing KeyReference.
 */
@Service
@Transactional
public class KeyReferenceServiceImpl implements KeyReferenceService {

    private final Logger log = LoggerFactory.getLogger(KeyReferenceServiceImpl.class);

    private final KeyReferenceRepository keyReferenceRepository;

    private final KeyReferenceMapper keyReferenceMapper;

    private final KeyReferenceSearchRepository keyReferenceSearchRepository;

    public KeyReferenceServiceImpl(KeyReferenceRepository keyReferenceRepository, KeyReferenceMapper keyReferenceMapper, KeyReferenceSearchRepository keyReferenceSearchRepository) {
        this.keyReferenceRepository = keyReferenceRepository;
        this.keyReferenceMapper = keyReferenceMapper;
        this.keyReferenceSearchRepository = keyReferenceSearchRepository;
    }

    /**
     * Save a keyReference.
     *
     * @param keyReferenceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public KeyReferenceDTO save(KeyReferenceDTO keyReferenceDTO) {
        log.debug("Request to save KeyReference : {}", keyReferenceDTO);
        KeyReference keyReference = keyReferenceMapper.toEntity(keyReferenceDTO);
        keyReference = keyReferenceRepository.save(keyReference);
        KeyReferenceDTO result = keyReferenceMapper.toDto(keyReference);
        keyReferenceSearchRepository.save(keyReference);
        return result;
    }

    /**
     * Get all the keyReferences.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<KeyReferenceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all KeyReferences");
        return keyReferenceRepository.findAll(pageable)
            .map(keyReferenceMapper::toDto);
    }

    /**
     * Get one keyReference by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public KeyReferenceDTO findOne(Long id) {
        log.debug("Request to get KeyReference : {}", id);
        KeyReference keyReference = keyReferenceRepository.findOne(id);
        return keyReferenceMapper.toDto(keyReference);
    }

    /**
     * Delete the keyReference by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete KeyReference : {}", id);
        keyReferenceRepository.delete(id);
        keyReferenceSearchRepository.delete(id);
    }

    /**
     * Search for the keyReference corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<KeyReferenceDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of KeyReferences for query {}", query);
        Page<KeyReference> result = keyReferenceSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(keyReferenceMapper::toDto);
    }
}
