package ch.fer.epost.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import ch.fer.epost.application.service.DocumentsSendService;
import ch.fer.epost.application.web.rest.errors.BadRequestAlertException;
import ch.fer.epost.application.web.rest.util.HeaderUtil;
import ch.fer.epost.application.web.rest.util.PaginationUtil;
import ch.fer.epost.application.service.dto.DocumentsSendDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing DocumentsSend.
 */
@RestController
@RequestMapping("/api")
public class DocumentsSendResource {

    private final Logger log = LoggerFactory.getLogger(DocumentsSendResource.class);

    private static final String ENTITY_NAME = "documentsSend";

    private final DocumentsSendService documentsSendService;

    public DocumentsSendResource(DocumentsSendService documentsSendService) {
        this.documentsSendService = documentsSendService;
    }

    /**
     * POST  /documents-sends : Create a new documentsSend.
     *
     * @param documentsSendDTO the documentsSendDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new documentsSendDTO, or with status 400 (Bad Request) if the documentsSend has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/documents-sends")
    @Timed
    public ResponseEntity<DocumentsSendDTO> createDocumentsSend(@RequestBody DocumentsSendDTO documentsSendDTO) throws URISyntaxException {
        log.debug("REST request to save DocumentsSend : {}", documentsSendDTO);
        if (documentsSendDTO.getId() != null) {
            throw new BadRequestAlertException("A new documentsSend cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocumentsSendDTO result = documentsSendService.save(documentsSendDTO);
        return ResponseEntity.created(new URI("/api/documents-sends/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /documents-sends : Updates an existing documentsSend.
     *
     * @param documentsSendDTO the documentsSendDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated documentsSendDTO,
     * or with status 400 (Bad Request) if the documentsSendDTO is not valid,
     * or with status 500 (Internal Server Error) if the documentsSendDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/documents-sends")
    @Timed
    public ResponseEntity<DocumentsSendDTO> updateDocumentsSend(@RequestBody DocumentsSendDTO documentsSendDTO) throws URISyntaxException {
        log.debug("REST request to update DocumentsSend : {}", documentsSendDTO);
        if (documentsSendDTO.getId() == null) {
            return createDocumentsSend(documentsSendDTO);
        }
        DocumentsSendDTO result = documentsSendService.save(documentsSendDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, documentsSendDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /documents-sends : get all the documentsSends.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of documentsSends in body
     */
    @GetMapping("/documents-sends")
    @Timed
    public ResponseEntity<List<DocumentsSendDTO>> getAllDocumentsSends(Pageable pageable) {
        log.debug("REST request to get a page of DocumentsSends");
        Page<DocumentsSendDTO> page = documentsSendService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/documents-sends");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /documents-sends/:id : get the "id" documentsSend.
     *
     * @param id the id of the documentsSendDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the documentsSendDTO, or with status 404 (Not Found)
     */
    @GetMapping("/documents-sends/{id}")
    @Timed
    public ResponseEntity<DocumentsSendDTO> getDocumentsSend(@PathVariable Long id) {
        log.debug("REST request to get DocumentsSend : {}", id);
        DocumentsSendDTO documentsSendDTO = documentsSendService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(documentsSendDTO));
    }

    /**
     * DELETE  /documents-sends/:id : delete the "id" documentsSend.
     *
     * @param id the id of the documentsSendDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/documents-sends/{id}")
    @Timed
    public ResponseEntity<Void> deleteDocumentsSend(@PathVariable Long id) {
        log.debug("REST request to delete DocumentsSend : {}", id);
        documentsSendService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/documents-sends?query=:query : search for the documentsSend corresponding
     * to the query.
     *
     * @param query the query of the documentsSend search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/documents-sends")
    @Timed
    public ResponseEntity<List<DocumentsSendDTO>> searchDocumentsSends(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DocumentsSends for query {}", query);
        Page<DocumentsSendDTO> page = documentsSendService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/documents-sends");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
