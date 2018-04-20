package ch.fer.epost.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import ch.fer.epost.application.service.DataReferenceService;
import ch.fer.epost.application.web.rest.errors.BadRequestAlertException;
import ch.fer.epost.application.web.rest.util.HeaderUtil;
import ch.fer.epost.application.web.rest.util.PaginationUtil;
import ch.fer.epost.application.service.dto.DataReferenceDTO;
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
 * REST controller for managing DataReference.
 */
@RestController
@RequestMapping("/api")
public class DataReferenceResource {

    private final Logger log = LoggerFactory.getLogger(DataReferenceResource.class);

    private static final String ENTITY_NAME = "dataReference";

    private final DataReferenceService dataReferenceService;

    public DataReferenceResource(DataReferenceService dataReferenceService) {
        this.dataReferenceService = dataReferenceService;
    }

    /**
     * POST  /data-references : Create a new dataReference.
     *
     * @param dataReferenceDTO the dataReferenceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dataReferenceDTO, or with status 400 (Bad Request) if the dataReference has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/data-references")
    @Timed
    public ResponseEntity<DataReferenceDTO> createDataReference(@RequestBody DataReferenceDTO dataReferenceDTO) throws URISyntaxException {
        log.debug("REST request to save DataReference : {}", dataReferenceDTO);
        if (dataReferenceDTO.getId() != null) {
            throw new BadRequestAlertException("A new dataReference cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DataReferenceDTO result = dataReferenceService.save(dataReferenceDTO);
        return ResponseEntity.created(new URI("/api/data-references/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /data-references : Updates an existing dataReference.
     *
     * @param dataReferenceDTO the dataReferenceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dataReferenceDTO,
     * or with status 400 (Bad Request) if the dataReferenceDTO is not valid,
     * or with status 500 (Internal Server Error) if the dataReferenceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/data-references")
    @Timed
    public ResponseEntity<DataReferenceDTO> updateDataReference(@RequestBody DataReferenceDTO dataReferenceDTO) throws URISyntaxException {
        log.debug("REST request to update DataReference : {}", dataReferenceDTO);
        if (dataReferenceDTO.getId() == null) {
            return createDataReference(dataReferenceDTO);
        }
        DataReferenceDTO result = dataReferenceService.save(dataReferenceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dataReferenceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /data-references : get all the dataReferences.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of dataReferences in body
     */
    @GetMapping("/data-references")
    @Timed
    public ResponseEntity<List<DataReferenceDTO>> getAllDataReferences(Pageable pageable) {
        log.debug("REST request to get a page of DataReferences");
        Page<DataReferenceDTO> page = dataReferenceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/data-references");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /data-references/:id : get the "id" dataReference.
     *
     * @param id the id of the dataReferenceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dataReferenceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/data-references/{id}")
    @Timed
    public ResponseEntity<DataReferenceDTO> getDataReference(@PathVariable Long id) {
        log.debug("REST request to get DataReference : {}", id);
        DataReferenceDTO dataReferenceDTO = dataReferenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dataReferenceDTO));
    }

    /**
     * DELETE  /data-references/:id : delete the "id" dataReference.
     *
     * @param id the id of the dataReferenceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/data-references/{id}")
    @Timed
    public ResponseEntity<Void> deleteDataReference(@PathVariable Long id) {
        log.debug("REST request to delete DataReference : {}", id);
        dataReferenceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/data-references?query=:query : search for the dataReference corresponding
     * to the query.
     *
     * @param query the query of the dataReference search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/data-references")
    @Timed
    public ResponseEntity<List<DataReferenceDTO>> searchDataReferences(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DataReferences for query {}", query);
        Page<DataReferenceDTO> page = dataReferenceService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/data-references");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
