package ch.fer.epost.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import ch.fer.epost.application.service.KeyReferenceService;
import ch.fer.epost.application.web.rest.errors.BadRequestAlertException;
import ch.fer.epost.application.web.rest.util.HeaderUtil;
import ch.fer.epost.application.web.rest.util.PaginationUtil;
import ch.fer.epost.application.service.dto.KeyReferenceDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing KeyReference.
 */
@RestController
@RequestMapping("/api")
public class KeyReferenceResource {

    private final Logger log = LoggerFactory.getLogger(KeyReferenceResource.class);

    private static final String ENTITY_NAME = "keyReference";

    private final KeyReferenceService keyReferenceService;

    public KeyReferenceResource(KeyReferenceService keyReferenceService) {
        this.keyReferenceService = keyReferenceService;
    }

    /**
     * POST  /key-references : Create a new keyReference.
     *
     * @param keyReferenceDTO the keyReferenceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new keyReferenceDTO, or with status 400 (Bad Request) if the keyReference has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/key-references")
    @Timed
    public ResponseEntity<KeyReferenceDTO> createKeyReference(@Valid @RequestBody KeyReferenceDTO keyReferenceDTO) throws URISyntaxException {
        log.debug("REST request to save KeyReference : {}", keyReferenceDTO);
        if (keyReferenceDTO.getId() != null) {
            throw new BadRequestAlertException("A new keyReference cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KeyReferenceDTO result = keyReferenceService.save(keyReferenceDTO);
        return ResponseEntity.created(new URI("/api/key-references/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /key-references : Updates an existing keyReference.
     *
     * @param keyReferenceDTO the keyReferenceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated keyReferenceDTO,
     * or with status 400 (Bad Request) if the keyReferenceDTO is not valid,
     * or with status 500 (Internal Server Error) if the keyReferenceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/key-references")
    @Timed
    public ResponseEntity<KeyReferenceDTO> updateKeyReference(@Valid @RequestBody KeyReferenceDTO keyReferenceDTO) throws URISyntaxException {
        log.debug("REST request to update KeyReference : {}", keyReferenceDTO);
        if (keyReferenceDTO.getId() == null) {
            return createKeyReference(keyReferenceDTO);
        }
        KeyReferenceDTO result = keyReferenceService.save(keyReferenceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, keyReferenceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /key-references : get all the keyReferences.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of keyReferences in body
     */
    @GetMapping("/key-references")
    @Timed
    public ResponseEntity<List<KeyReferenceDTO>> getAllKeyReferences(Pageable pageable) {
        log.debug("REST request to get a page of KeyReferences");
        Page<KeyReferenceDTO> page = keyReferenceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/key-references");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /key-references/:id : get the "id" keyReference.
     *
     * @param id the id of the keyReferenceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the keyReferenceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/key-references/{id}")
    @Timed
    public ResponseEntity<KeyReferenceDTO> getKeyReference(@PathVariable Long id) {
        log.debug("REST request to get KeyReference : {}", id);
        KeyReferenceDTO keyReferenceDTO = keyReferenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(keyReferenceDTO));
    }

    /**
     * DELETE  /key-references/:id : delete the "id" keyReference.
     *
     * @param id the id of the keyReferenceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/key-references/{id}")
    @Timed
    public ResponseEntity<Void> deleteKeyReference(@PathVariable Long id) {
        log.debug("REST request to delete KeyReference : {}", id);
        keyReferenceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/key-references?query=:query : search for the keyReference corresponding
     * to the query.
     *
     * @param query the query of the keyReference search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/key-references")
    @Timed
    public ResponseEntity<List<KeyReferenceDTO>> searchKeyReferences(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of KeyReferences for query {}", query);
        Page<KeyReferenceDTO> page = keyReferenceService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/key-references");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
