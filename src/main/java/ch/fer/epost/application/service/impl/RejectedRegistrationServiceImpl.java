package ch.fer.epost.application.service.impl;

import ch.fer.epost.application.service.RejectedRegistrationService;
import ch.fer.epost.application.domain.RejectedRegistration;
import ch.fer.epost.application.repository.RejectedRegistrationRepository;
import ch.fer.epost.application.repository.search.RejectedRegistrationSearchRepository;
import ch.fer.epost.application.service.dto.RejectedRegistrationDTO;
import ch.fer.epost.application.service.mapper.RejectedRegistrationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing RejectedRegistration.
 */
@Service
@Transactional
public class RejectedRegistrationServiceImpl implements RejectedRegistrationService {

    private final Logger log = LoggerFactory.getLogger(RejectedRegistrationServiceImpl.class);

    private final RejectedRegistrationRepository rejectedRegistrationRepository;

    private final RejectedRegistrationMapper rejectedRegistrationMapper;

    private final RejectedRegistrationSearchRepository rejectedRegistrationSearchRepository;

    public RejectedRegistrationServiceImpl(RejectedRegistrationRepository rejectedRegistrationRepository, RejectedRegistrationMapper rejectedRegistrationMapper, RejectedRegistrationSearchRepository rejectedRegistrationSearchRepository) {
        this.rejectedRegistrationRepository = rejectedRegistrationRepository;
        this.rejectedRegistrationMapper = rejectedRegistrationMapper;
        this.rejectedRegistrationSearchRepository = rejectedRegistrationSearchRepository;
    }

    /**
     * Save a rejectedRegistration.
     *
     * @param rejectedRegistrationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RejectedRegistrationDTO save(RejectedRegistrationDTO rejectedRegistrationDTO) {
        log.debug("Request to save RejectedRegistration : {}", rejectedRegistrationDTO);
        RejectedRegistration rejectedRegistration = rejectedRegistrationMapper.toEntity(rejectedRegistrationDTO);
        rejectedRegistration = rejectedRegistrationRepository.save(rejectedRegistration);
        RejectedRegistrationDTO result = rejectedRegistrationMapper.toDto(rejectedRegistration);
        rejectedRegistrationSearchRepository.save(rejectedRegistration);
        return result;
    }

    /**
     * Get all the rejectedRegistrations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RejectedRegistrationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RejectedRegistrations");
        return rejectedRegistrationRepository.findAll(pageable)
            .map(rejectedRegistrationMapper::toDto);
    }

    /**
     * Get one rejectedRegistration by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RejectedRegistrationDTO findOne(Long id) {
        log.debug("Request to get RejectedRegistration : {}", id);
        RejectedRegistration rejectedRegistration = rejectedRegistrationRepository.findOne(id);
        return rejectedRegistrationMapper.toDto(rejectedRegistration);
    }

    /**
     * Delete the rejectedRegistration by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RejectedRegistration : {}", id);
        rejectedRegistrationRepository.delete(id);
        rejectedRegistrationSearchRepository.delete(id);
    }

    /**
     * Search for the rejectedRegistration corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RejectedRegistrationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RejectedRegistrations for query {}", query);
        Page<RejectedRegistration> result = rejectedRegistrationSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(rejectedRegistrationMapper::toDto);
    }
}
