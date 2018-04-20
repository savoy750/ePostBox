package ch.fer.epost.application.web.rest;

import ch.fer.epost.application.EPostBoxApp;

import ch.fer.epost.application.domain.DocumentsSend;
import ch.fer.epost.application.repository.DocumentsSendRepository;
import ch.fer.epost.application.service.DocumentsSendService;
import ch.fer.epost.application.repository.search.DocumentsSendSearchRepository;
import ch.fer.epost.application.service.dto.DocumentsSendDTO;
import ch.fer.epost.application.service.mapper.DocumentsSendMapper;
import ch.fer.epost.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static ch.fer.epost.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ch.fer.epost.application.domain.enumeration.Status;
/**
 * Test class for the DocumentsSendResource REST controller.
 *
 * @see DocumentsSendResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EPostBoxApp.class)
public class DocumentsSendResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CORRELATION_ID = "AAAAAAAAAA";
    private static final String UPDATED_CORRELATION_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_DOCUMENT_TYPE = 1;
    private static final Integer UPDATED_DOCUMENT_TYPE = 2;

    private static final String DEFAULT_TAG = "AAAAAAAAAA";
    private static final String UPDATED_TAG = "BBBBBBBBBB";

    private static final String DEFAULT_INTERNAL_KEY = "AAAAAAAAAA";
    private static final String UPDATED_INTERNAL_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.CREATED;
    private static final Status UPDATED_STATUS = Status.ERROR;

    @Autowired
    private DocumentsSendRepository documentsSendRepository;

    @Autowired
    private DocumentsSendMapper documentsSendMapper;

    @Autowired
    private DocumentsSendService documentsSendService;

    @Autowired
    private DocumentsSendSearchRepository documentsSendSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDocumentsSendMockMvc;

    private DocumentsSend documentsSend;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DocumentsSendResource documentsSendResource = new DocumentsSendResource(documentsSendService);
        this.restDocumentsSendMockMvc = MockMvcBuilders.standaloneSetup(documentsSendResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentsSend createEntity(EntityManager em) {
        DocumentsSend documentsSend = new DocumentsSend()
            .title(DEFAULT_TITLE)
            .correlationId(DEFAULT_CORRELATION_ID)
            .documentType(DEFAULT_DOCUMENT_TYPE)
            .tag(DEFAULT_TAG)
            .internalKey(DEFAULT_INTERNAL_KEY)
            .message(DEFAULT_MESSAGE)
            .status(DEFAULT_STATUS);
        return documentsSend;
    }

    @Before
    public void initTest() {
        documentsSendSearchRepository.deleteAll();
        documentsSend = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocumentsSend() throws Exception {
        int databaseSizeBeforeCreate = documentsSendRepository.findAll().size();

        // Create the DocumentsSend
        DocumentsSendDTO documentsSendDTO = documentsSendMapper.toDto(documentsSend);
        restDocumentsSendMockMvc.perform(post("/api/documents-sends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentsSendDTO)))
            .andExpect(status().isCreated());

        // Validate the DocumentsSend in the database
        List<DocumentsSend> documentsSendList = documentsSendRepository.findAll();
        assertThat(documentsSendList).hasSize(databaseSizeBeforeCreate + 1);
        DocumentsSend testDocumentsSend = documentsSendList.get(documentsSendList.size() - 1);
        assertThat(testDocumentsSend.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testDocumentsSend.getCorrelationId()).isEqualTo(DEFAULT_CORRELATION_ID);
        assertThat(testDocumentsSend.getDocumentType()).isEqualTo(DEFAULT_DOCUMENT_TYPE);
        assertThat(testDocumentsSend.getTag()).isEqualTo(DEFAULT_TAG);
        assertThat(testDocumentsSend.getInternalKey()).isEqualTo(DEFAULT_INTERNAL_KEY);
        assertThat(testDocumentsSend.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testDocumentsSend.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the DocumentsSend in Elasticsearch
        DocumentsSend documentsSendEs = documentsSendSearchRepository.findOne(testDocumentsSend.getId());
        assertThat(documentsSendEs).isEqualToIgnoringGivenFields(testDocumentsSend);
    }

    @Test
    @Transactional
    public void createDocumentsSendWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = documentsSendRepository.findAll().size();

        // Create the DocumentsSend with an existing ID
        documentsSend.setId(1L);
        DocumentsSendDTO documentsSendDTO = documentsSendMapper.toDto(documentsSend);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentsSendMockMvc.perform(post("/api/documents-sends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentsSendDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentsSend in the database
        List<DocumentsSend> documentsSendList = documentsSendRepository.findAll();
        assertThat(documentsSendList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDocumentsSends() throws Exception {
        // Initialize the database
        documentsSendRepository.saveAndFlush(documentsSend);

        // Get all the documentsSendList
        restDocumentsSendMockMvc.perform(get("/api/documents-sends?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentsSend.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].correlationId").value(hasItem(DEFAULT_CORRELATION_ID.toString())))
            .andExpect(jsonPath("$.[*].documentType").value(hasItem(DEFAULT_DOCUMENT_TYPE)))
            .andExpect(jsonPath("$.[*].tag").value(hasItem(DEFAULT_TAG.toString())))
            .andExpect(jsonPath("$.[*].internalKey").value(hasItem(DEFAULT_INTERNAL_KEY.toString())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getDocumentsSend() throws Exception {
        // Initialize the database
        documentsSendRepository.saveAndFlush(documentsSend);

        // Get the documentsSend
        restDocumentsSendMockMvc.perform(get("/api/documents-sends/{id}", documentsSend.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(documentsSend.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.correlationId").value(DEFAULT_CORRELATION_ID.toString()))
            .andExpect(jsonPath("$.documentType").value(DEFAULT_DOCUMENT_TYPE))
            .andExpect(jsonPath("$.tag").value(DEFAULT_TAG.toString()))
            .andExpect(jsonPath("$.internalKey").value(DEFAULT_INTERNAL_KEY.toString()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDocumentsSend() throws Exception {
        // Get the documentsSend
        restDocumentsSendMockMvc.perform(get("/api/documents-sends/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocumentsSend() throws Exception {
        // Initialize the database
        documentsSendRepository.saveAndFlush(documentsSend);
        documentsSendSearchRepository.save(documentsSend);
        int databaseSizeBeforeUpdate = documentsSendRepository.findAll().size();

        // Update the documentsSend
        DocumentsSend updatedDocumentsSend = documentsSendRepository.findOne(documentsSend.getId());
        // Disconnect from session so that the updates on updatedDocumentsSend are not directly saved in db
        em.detach(updatedDocumentsSend);
        updatedDocumentsSend
            .title(UPDATED_TITLE)
            .correlationId(UPDATED_CORRELATION_ID)
            .documentType(UPDATED_DOCUMENT_TYPE)
            .tag(UPDATED_TAG)
            .internalKey(UPDATED_INTERNAL_KEY)
            .message(UPDATED_MESSAGE)
            .status(UPDATED_STATUS);
        DocumentsSendDTO documentsSendDTO = documentsSendMapper.toDto(updatedDocumentsSend);

        restDocumentsSendMockMvc.perform(put("/api/documents-sends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentsSendDTO)))
            .andExpect(status().isOk());

        // Validate the DocumentsSend in the database
        List<DocumentsSend> documentsSendList = documentsSendRepository.findAll();
        assertThat(documentsSendList).hasSize(databaseSizeBeforeUpdate);
        DocumentsSend testDocumentsSend = documentsSendList.get(documentsSendList.size() - 1);
        assertThat(testDocumentsSend.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testDocumentsSend.getCorrelationId()).isEqualTo(UPDATED_CORRELATION_ID);
        assertThat(testDocumentsSend.getDocumentType()).isEqualTo(UPDATED_DOCUMENT_TYPE);
        assertThat(testDocumentsSend.getTag()).isEqualTo(UPDATED_TAG);
        assertThat(testDocumentsSend.getInternalKey()).isEqualTo(UPDATED_INTERNAL_KEY);
        assertThat(testDocumentsSend.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testDocumentsSend.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the DocumentsSend in Elasticsearch
        DocumentsSend documentsSendEs = documentsSendSearchRepository.findOne(testDocumentsSend.getId());
        assertThat(documentsSendEs).isEqualToIgnoringGivenFields(testDocumentsSend);
    }

    @Test
    @Transactional
    public void updateNonExistingDocumentsSend() throws Exception {
        int databaseSizeBeforeUpdate = documentsSendRepository.findAll().size();

        // Create the DocumentsSend
        DocumentsSendDTO documentsSendDTO = documentsSendMapper.toDto(documentsSend);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDocumentsSendMockMvc.perform(put("/api/documents-sends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentsSendDTO)))
            .andExpect(status().isCreated());

        // Validate the DocumentsSend in the database
        List<DocumentsSend> documentsSendList = documentsSendRepository.findAll();
        assertThat(documentsSendList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDocumentsSend() throws Exception {
        // Initialize the database
        documentsSendRepository.saveAndFlush(documentsSend);
        documentsSendSearchRepository.save(documentsSend);
        int databaseSizeBeforeDelete = documentsSendRepository.findAll().size();

        // Get the documentsSend
        restDocumentsSendMockMvc.perform(delete("/api/documents-sends/{id}", documentsSend.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean documentsSendExistsInEs = documentsSendSearchRepository.exists(documentsSend.getId());
        assertThat(documentsSendExistsInEs).isFalse();

        // Validate the database is empty
        List<DocumentsSend> documentsSendList = documentsSendRepository.findAll();
        assertThat(documentsSendList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDocumentsSend() throws Exception {
        // Initialize the database
        documentsSendRepository.saveAndFlush(documentsSend);
        documentsSendSearchRepository.save(documentsSend);

        // Search the documentsSend
        restDocumentsSendMockMvc.perform(get("/api/_search/documents-sends?query=id:" + documentsSend.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentsSend.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].correlationId").value(hasItem(DEFAULT_CORRELATION_ID.toString())))
            .andExpect(jsonPath("$.[*].documentType").value(hasItem(DEFAULT_DOCUMENT_TYPE)))
            .andExpect(jsonPath("$.[*].tag").value(hasItem(DEFAULT_TAG.toString())))
            .andExpect(jsonPath("$.[*].internalKey").value(hasItem(DEFAULT_INTERNAL_KEY.toString())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentsSend.class);
        DocumentsSend documentsSend1 = new DocumentsSend();
        documentsSend1.setId(1L);
        DocumentsSend documentsSend2 = new DocumentsSend();
        documentsSend2.setId(documentsSend1.getId());
        assertThat(documentsSend1).isEqualTo(documentsSend2);
        documentsSend2.setId(2L);
        assertThat(documentsSend1).isNotEqualTo(documentsSend2);
        documentsSend1.setId(null);
        assertThat(documentsSend1).isNotEqualTo(documentsSend2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentsSendDTO.class);
        DocumentsSendDTO documentsSendDTO1 = new DocumentsSendDTO();
        documentsSendDTO1.setId(1L);
        DocumentsSendDTO documentsSendDTO2 = new DocumentsSendDTO();
        assertThat(documentsSendDTO1).isNotEqualTo(documentsSendDTO2);
        documentsSendDTO2.setId(documentsSendDTO1.getId());
        assertThat(documentsSendDTO1).isEqualTo(documentsSendDTO2);
        documentsSendDTO2.setId(2L);
        assertThat(documentsSendDTO1).isNotEqualTo(documentsSendDTO2);
        documentsSendDTO1.setId(null);
        assertThat(documentsSendDTO1).isNotEqualTo(documentsSendDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(documentsSendMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(documentsSendMapper.fromId(null)).isNull();
    }
}
