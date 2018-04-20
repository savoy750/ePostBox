package ch.fer.epost.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import ch.fer.epost.application.service.RejectedRegistrationService;
import ch.fer.epost.application.web.rest.errors.BadRequestAlertException;
import ch.fer.epost.application.web.rest.util.HeaderUtil;
import ch.fer.epost.application.web.rest.util.PaginationUtil;
import ch.fer.epost.application.service.dto.RejectedRegistrationDTO;
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
 * REST controller for managing RejectedRegistration.
 */
@RestController
@RequestMapping("/api")
public class RejectedRegistrationResource {

    private final Logger log = LoggerFactory.getLogger(RejectedRegistrationResource.class);

    private static final String ENTITY_NAME = "rejectedRegistration";

    private final RejectedRegistrationService rejectedRegistrationService;

    public RejectedRegistrationResource(RejectedRegistrationService rejectedRegistrationService) {
        this.rejectedRegistrationService = rejectedRegistrationService;
    }

    /**
     * POST  /rejected-registrations : Create a new rejectedRegistration.
     *
     * @param rejectedRegistrationDTO the rejectedRegistrationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rejectedRegistrationDTO, or with status 400 (Bad Request) if the rejectedRegistration has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rejected-registrations")
    @Timed
    public ResponseEntity<RejectedRegistrationDTO> createRejectedRegistration(@Valid @RequestBody RejectedRegistrationDTO rejectedRegistrationDTO) throws URISyntaxException {
        log.debug("REST request to save RejectedRegistration : {}", rejectedRegistrationDTO);
        if (rejectedRegistrationDTO.getId() != null) {
            throw new BadRequestAlertException("A new rejectedRegistration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RejectedRegistrationDTO result = rejectedRegistrationService.save(rejectedRegistrationDTO);
        return ResponseEntity.created(new URI("/api/rejected-registrations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rejected-registrations : Updates an existing rejectedRegistration.
     *
     * @param rejectedRegistrationDTO the rejectedRegistrationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rejectedRegistrationDTO,
     * or with status 400 (Bad Request) if the rejectedRegistrationDTO is not valid,
     * or with status 500 (Internal Server Error) if the rejectedRegistrationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rejected-registrations")
    @Timed
    public ResponseEntity<RejectedRegistrationDTO> updateRejectedRegistration(@Valid @RequestBody RejectedRegistrationDTO rejectedRegistrationDTO) throws URISyntaxException {
        log.debug("REST request to update RejectedRegistration : {}", rejectedRegistrationDTO);
        if (rejectedRegistrationDTO.getId() == null) {
            return createRejectedRegistration(rejectedRegistrationDTO);
        }
        RejectedRegistrationDTO result = rejectedRegistrationService.save(rejectedRegistrationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rejectedRegistrationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rejected-registrations : get all the rejectedRegistrations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of rejectedRegistrations in body
     */
    @GetMapping("/rejected-registrations")
    @Timed
    public ResponseEntity<List<RejectedRegistrationDTO>> getAllRejectedRegistrations(Pageable pageable) {
        log.debug("REST request to get a page of RejectedRegistrations");
        Page<RejectedRegistrationDTO> page = rejectedRegistrationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rejected-registrations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /rejected-registrations/:id : get the "id" rejectedRegistration.
     *
     * @param id the id of the rejectedRegistrationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rejectedRegistrationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/rejected-registrations/{id}")
    @Timed
    public ResponseEntity<RejectedRegistrationDTO> getRejectedRegistration(@PathVariable Long id) {
        log.debug("REST request to get RejectedRegistration : {}", id);
        RejectedRegistrationDTO rejectedRegistrationDTO = rejectedRegistrationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rejectedRegistrationDTO));
    }

    /**
     * DELETE  /rejected-registrations/:id : delete the "id" rejectedRegistration.
     *
     * @param id the id of the rejectedRegistrationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rejected-registrations/{id}")
    @Timed
    public ResponseEntity<Void> deleteRejectedRegistration(@PathVariable Long id) {
        log.debug("REST request to delete RejectedRegistration : {}", id);
        rejectedRegistrationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/rejected-registrations?query=:query : search for the rejectedRegistration corresponding
     * to the query.
     *
     * @param query the query of the rejectedRegistration search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/rejected-registrations")
    @Timed
    public ResponseEntity<List<RejectedRegistrationDTO>> searchRejectedRegistrations(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of RejectedRegistrations for query {}", query);
        Page<RejectedRegistrationDTO> page = rejectedRegistrationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/rejected-registrations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
