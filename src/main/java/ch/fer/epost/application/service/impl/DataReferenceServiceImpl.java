package ch.fer.epost.application.service.impl;

import ch.fer.epost.application.service.DataReferenceService;
import ch.fer.epost.application.domain.DataReference;
import ch.fer.epost.application.repository.DataReferenceRepository;
import ch.fer.epost.application.repository.search.DataReferenceSearchRepository;
import ch.fer.epost.application.service.dto.DataReferenceDTO;
import ch.fer.epost.application.service.mapper.DataReferenceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing DataReference.
 */
@Service
@Transactional
public class DataReferenceServiceImpl implements DataReferenceService {

    private final Logger log = LoggerFactory.getLogger(DataReferenceServiceImpl.class);

    private final DataReferenceRepository dataReferenceRepository;

    private final DataReferenceMapper dataReferenceMapper;

    private final DataReferenceSearchRepository dataReferenceSearchRepository;

    public DataReferenceServiceImpl(DataReferenceRepository dataReferenceRepository, DataReferenceMapper dataReferenceMapper, DataReferenceSearchRepository dataReferenceSearchRepository) {
        this.dataReferenceRepository = dataReferenceRepository;
        this.dataReferenceMapper = dataReferenceMapper;
        this.dataReferenceSearchRepository = dataReferenceSearchRepository;
    }

    /**
     * Save a dataReference.
     *
     * @param dataReferenceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DataReferenceDTO save(DataReferenceDTO dataReferenceDTO) {
        log.debug("Request to save DataReference : {}", dataReferenceDTO);
        DataReference dataReference = dataReferenceMapper.toEntity(dataReferenceDTO);
        dataReference = dataReferenceRepository.save(dataReference);
        DataReferenceDTO result = dataReferenceMapper.toDto(dataReference);
        dataReferenceSearchRepository.save(dataReference);
        return result;
    }

    /**
     * Get all the dataReferences.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DataReferenceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DataReferences");
        return dataReferenceRepository.findAll(pageable)
            .map(dataReferenceMapper::toDto);
    }

    /**
     * Get one dataReference by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DataReferenceDTO findOne(Long id) {
        log.debug("Request to get DataReference : {}", id);
        DataReference dataReference = dataReferenceRepository.findOne(id);
        return dataReferenceMapper.toDto(dataReference);
    }

    /**
     * Delete the dataReference by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DataReference : {}", id);
        dataReferenceRepository.delete(id);
        dataReferenceSearchRepository.delete(id);
    }

    /**
     * Search for the dataReference corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DataReferenceDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DataReferences for query {}", query);
        Page<DataReference> result = dataReferenceSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(dataReferenceMapper::toDto);
    }
}
