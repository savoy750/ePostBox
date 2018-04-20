package ch.fer.epost.application.service.impl;

import ch.fer.epost.application.service.DocumentsSendService;
import ch.fer.epost.application.domain.DocumentsSend;
import ch.fer.epost.application.repository.DocumentsSendRepository;
import ch.fer.epost.application.repository.search.DocumentsSendSearchRepository;
import ch.fer.epost.application.service.dto.DocumentsSendDTO;
import ch.fer.epost.application.service.mapper.DocumentsSendMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing DocumentsSend.
 */
@Service
@Transactional
public class DocumentsSendServiceImpl implements DocumentsSendService {

    private final Logger log = LoggerFactory.getLogger(DocumentsSendServiceImpl.class);

    private final DocumentsSendRepository documentsSendRepository;

    private final DocumentsSendMapper documentsSendMapper;

    private final DocumentsSendSearchRepository documentsSendSearchRepository;

    public DocumentsSendServiceImpl(DocumentsSendRepository documentsSendRepository, DocumentsSendMapper documentsSendMapper, DocumentsSendSearchRepository documentsSendSearchRepository) {
        this.documentsSendRepository = documentsSendRepository;
        this.documentsSendMapper = documentsSendMapper;
        this.documentsSendSearchRepository = documentsSendSearchRepository;
    }

    /**
     * Save a documentsSend.
     *
     * @param documentsSendDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DocumentsSendDTO save(DocumentsSendDTO documentsSendDTO) {
        log.debug("Request to save DocumentsSend : {}", documentsSendDTO);
        DocumentsSend documentsSend = documentsSendMapper.toEntity(documentsSendDTO);
        documentsSend = documentsSendRepository.save(documentsSend);
        DocumentsSendDTO result = documentsSendMapper.toDto(documentsSend);
        documentsSendSearchRepository.save(documentsSend);
        return result;
    }

    /**
     * Get all the documentsSends.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DocumentsSendDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DocumentsSends");
        return documentsSendRepository.findAll(pageable)
            .map(documentsSendMapper::toDto);
    }

    /**
     * Get one documentsSend by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DocumentsSendDTO findOne(Long id) {
        log.debug("Request to get DocumentsSend : {}", id);
        DocumentsSend documentsSend = documentsSendRepository.findOne(id);
        return documentsSendMapper.toDto(documentsSend);
    }

    /**
     * Delete the documentsSend by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DocumentsSend : {}", id);
        documentsSendRepository.delete(id);
        documentsSendSearchRepository.delete(id);
    }

    /**
     * Search for the documentsSend corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DocumentsSendDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DocumentsSends for query {}", query);
        Page<DocumentsSend> result = documentsSendSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(documentsSendMapper::toDto);
    }
}
