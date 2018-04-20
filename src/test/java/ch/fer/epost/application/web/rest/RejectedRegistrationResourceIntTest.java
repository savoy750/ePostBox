package ch.fer.epost.application.web.rest;

import ch.fer.epost.application.EPostBoxApp;

import ch.fer.epost.application.domain.RejectedRegistration;
import ch.fer.epost.application.repository.RejectedRegistrationRepository;
import ch.fer.epost.application.service.RejectedRegistrationService;
import ch.fer.epost.application.repository.search.RejectedRegistrationSearchRepository;
import ch.fer.epost.application.service.dto.RejectedRegistrationDTO;
import ch.fer.epost.application.service.mapper.RejectedRegistrationMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ch.fer.epost.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RejectedRegistrationResource REST controller.
 *
 * @see RejectedRegistrationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EPostBoxApp.class)
public class RejectedRegistrationResourceIntTest {

    private static final String DEFAULT_E_POST_KEY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_E_POST_KEY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_E_POST_KEY_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_E_POST_KEY_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_NO_AVS = "AAAAAAAAAA";
    private static final String UPDATED_NO_AVS = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_DE_NAISSANCE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_DE_NAISSANCE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private RejectedRegistrationRepository rejectedRegistrationRepository;

    @Autowired
    private RejectedRegistrationMapper rejectedRegistrationMapper;

    @Autowired
    private RejectedRegistrationService rejectedRegistrationService;

    @Autowired
    private RejectedRegistrationSearchRepository rejectedRegistrationSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRejectedRegistrationMockMvc;

    private RejectedRegistration rejectedRegistration;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RejectedRegistrationResource rejectedRegistrationResource = new RejectedRegistrationResource(rejectedRegistrationService);
        this.restRejectedRegistrationMockMvc = MockMvcBuilders.standaloneSetup(rejectedRegistrationResource)
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
    public static RejectedRegistration createEntity(EntityManager em) {
        RejectedRegistration rejectedRegistration = new RejectedRegistration()
            .ePostKeyName(DEFAULT_E_POST_KEY_NAME)
            .ePostKeyValue(DEFAULT_E_POST_KEY_VALUE)
            .noAVS(DEFAULT_NO_AVS)
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .dateDeNaissance(DEFAULT_DATE_DE_NAISSANCE);
        return rejectedRegistration;
    }

    @Before
    public void initTest() {
        rejectedRegistrationSearchRepository.deleteAll();
        rejectedRegistration = createEntity(em);
    }

    @Test
    @Transactional
    public void createRejectedRegistration() throws Exception {
        int databaseSizeBeforeCreate = rejectedRegistrationRepository.findAll().size();

        // Create the RejectedRegistration
        RejectedRegistrationDTO rejectedRegistrationDTO = rejectedRegistrationMapper.toDto(rejectedRegistration);
        restRejectedRegistrationMockMvc.perform(post("/api/rejected-registrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rejectedRegistrationDTO)))
            .andExpect(status().isCreated());

        // Validate the RejectedRegistration in the database
        List<RejectedRegistration> rejectedRegistrationList = rejectedRegistrationRepository.findAll();
        assertThat(rejectedRegistrationList).hasSize(databaseSizeBeforeCreate + 1);
        RejectedRegistration testRejectedRegistration = rejectedRegistrationList.get(rejectedRegistrationList.size() - 1);
        assertThat(testRejectedRegistration.getePostKeyName()).isEqualTo(DEFAULT_E_POST_KEY_NAME);
        assertThat(testRejectedRegistration.getePostKeyValue()).isEqualTo(DEFAULT_E_POST_KEY_VALUE);
        assertThat(testRejectedRegistration.getNoAVS()).isEqualTo(DEFAULT_NO_AVS);
        assertThat(testRejectedRegistration.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testRejectedRegistration.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testRejectedRegistration.getDateDeNaissance()).isEqualTo(DEFAULT_DATE_DE_NAISSANCE);

        // Validate the RejectedRegistration in Elasticsearch
        RejectedRegistration rejectedRegistrationEs = rejectedRegistrationSearchRepository.findOne(testRejectedRegistration.getId());
        assertThat(rejectedRegistrationEs).isEqualToIgnoringGivenFields(testRejectedRegistration);
    }

    @Test
    @Transactional
    public void createRejectedRegistrationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rejectedRegistrationRepository.findAll().size();

        // Create the RejectedRegistration with an existing ID
        rejectedRegistration.setId(1L);
        RejectedRegistrationDTO rejectedRegistrationDTO = rejectedRegistrationMapper.toDto(rejectedRegistration);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRejectedRegistrationMockMvc.perform(post("/api/rejected-registrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rejectedRegistrationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RejectedRegistration in the database
        List<RejectedRegistration> rejectedRegistrationList = rejectedRegistrationRepository.findAll();
        assertThat(rejectedRegistrationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkePostKeyNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = rejectedRegistrationRepository.findAll().size();
        // set the field null
        rejectedRegistration.setePostKeyName(null);

        // Create the RejectedRegistration, which fails.
        RejectedRegistrationDTO rejectedRegistrationDTO = rejectedRegistrationMapper.toDto(rejectedRegistration);

        restRejectedRegistrationMockMvc.perform(post("/api/rejected-registrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rejectedRegistrationDTO)))
            .andExpect(status().isBadRequest());

        List<RejectedRegistration> rejectedRegistrationList = rejectedRegistrationRepository.findAll();
        assertThat(rejectedRegistrationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkePostKeyValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = rejectedRegistrationRepository.findAll().size();
        // set the field null
        rejectedRegistration.setePostKeyValue(null);

        // Create the RejectedRegistration, which fails.
        RejectedRegistrationDTO rejectedRegistrationDTO = rejectedRegistrationMapper.toDto(rejectedRegistration);

        restRejectedRegistrationMockMvc.perform(post("/api/rejected-registrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rejectedRegistrationDTO)))
            .andExpect(status().isBadRequest());

        List<RejectedRegistration> rejectedRegistrationList = rejectedRegistrationRepository.findAll();
        assertThat(rejectedRegistrationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRejectedRegistrations() throws Exception {
        // Initialize the database
        rejectedRegistrationRepository.saveAndFlush(rejectedRegistration);

        // Get all the rejectedRegistrationList
        restRejectedRegistrationMockMvc.perform(get("/api/rejected-registrations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rejectedRegistration.getId().intValue())))
            .andExpect(jsonPath("$.[*].ePostKeyName").value(hasItem(DEFAULT_E_POST_KEY_NAME.toString())))
            .andExpect(jsonPath("$.[*].ePostKeyValue").value(hasItem(DEFAULT_E_POST_KEY_VALUE.toString())))
            .andExpect(jsonPath("$.[*].noAVS").value(hasItem(DEFAULT_NO_AVS.toString())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
            .andExpect(jsonPath("$.[*].dateDeNaissance").value(hasItem(DEFAULT_DATE_DE_NAISSANCE.toString())));
    }

    @Test
    @Transactional
    public void getRejectedRegistration() throws Exception {
        // Initialize the database
        rejectedRegistrationRepository.saveAndFlush(rejectedRegistration);

        // Get the rejectedRegistration
        restRejectedRegistrationMockMvc.perform(get("/api/rejected-registrations/{id}", rejectedRegistration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rejectedRegistration.getId().intValue()))
            .andExpect(jsonPath("$.ePostKeyName").value(DEFAULT_E_POST_KEY_NAME.toString()))
            .andExpect(jsonPath("$.ePostKeyValue").value(DEFAULT_E_POST_KEY_VALUE.toString()))
            .andExpect(jsonPath("$.noAVS").value(DEFAULT_NO_AVS.toString()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM.toString()))
            .andExpect(jsonPath("$.dateDeNaissance").value(DEFAULT_DATE_DE_NAISSANCE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRejectedRegistration() throws Exception {
        // Get the rejectedRegistration
        restRejectedRegistrationMockMvc.perform(get("/api/rejected-registrations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRejectedRegistration() throws Exception {
        // Initialize the database
        rejectedRegistrationRepository.saveAndFlush(rejectedRegistration);
        rejectedRegistrationSearchRepository.save(rejectedRegistration);
        int databaseSizeBeforeUpdate = rejectedRegistrationRepository.findAll().size();

        // Update the rejectedRegistration
        RejectedRegistration updatedRejectedRegistration = rejectedRegistrationRepository.findOne(rejectedRegistration.getId());
        // Disconnect from session so that the updates on updatedRejectedRegistration are not directly saved in db
        em.detach(updatedRejectedRegistration);
        updatedRejectedRegistration
            .ePostKeyName(UPDATED_E_POST_KEY_NAME)
            .ePostKeyValue(UPDATED_E_POST_KEY_VALUE)
            .noAVS(UPDATED_NO_AVS)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .dateDeNaissance(UPDATED_DATE_DE_NAISSANCE);
        RejectedRegistrationDTO rejectedRegistrationDTO = rejectedRegistrationMapper.toDto(updatedRejectedRegistration);

        restRejectedRegistrationMockMvc.perform(put("/api/rejected-registrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rejectedRegistrationDTO)))
            .andExpect(status().isOk());

        // Validate the RejectedRegistration in the database
        List<RejectedRegistration> rejectedRegistrationList = rejectedRegistrationRepository.findAll();
        assertThat(rejectedRegistrationList).hasSize(databaseSizeBeforeUpdate);
        RejectedRegistration testRejectedRegistration = rejectedRegistrationList.get(rejectedRegistrationList.size() - 1);
        assertThat(testRejectedRegistration.getePostKeyName()).isEqualTo(UPDATED_E_POST_KEY_NAME);
        assertThat(testRejectedRegistration.getePostKeyValue()).isEqualTo(UPDATED_E_POST_KEY_VALUE);
        assertThat(testRejectedRegistration.getNoAVS()).isEqualTo(UPDATED_NO_AVS);
        assertThat(testRejectedRegistration.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testRejectedRegistration.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testRejectedRegistration.getDateDeNaissance()).isEqualTo(UPDATED_DATE_DE_NAISSANCE);

        // Validate the RejectedRegistration in Elasticsearch
        RejectedRegistration rejectedRegistrationEs = rejectedRegistrationSearchRepository.findOne(testRejectedRegistration.getId());
        assertThat(rejectedRegistrationEs).isEqualToIgnoringGivenFields(testRejectedRegistration);
    }

    @Test
    @Transactional
    public void updateNonExistingRejectedRegistration() throws Exception {
        int databaseSizeBeforeUpdate = rejectedRegistrationRepository.findAll().size();

        // Create the RejectedRegistration
        RejectedRegistrationDTO rejectedRegistrationDTO = rejectedRegistrationMapper.toDto(rejectedRegistration);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRejectedRegistrationMockMvc.perform(put("/api/rejected-registrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rejectedRegistrationDTO)))
            .andExpect(status().isCreated());

        // Validate the RejectedRegistration in the database
        List<RejectedRegistration> rejectedRegistrationList = rejectedRegistrationRepository.findAll();
        assertThat(rejectedRegistrationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRejectedRegistration() throws Exception {
        // Initialize the database
        rejectedRegistrationRepository.saveAndFlush(rejectedRegistration);
        rejectedRegistrationSearchRepository.save(rejectedRegistration);
        int databaseSizeBeforeDelete = rejectedRegistrationRepository.findAll().size();

        // Get the rejectedRegistration
        restRejectedRegistrationMockMvc.perform(delete("/api/rejected-registrations/{id}", rejectedRegistration.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean rejectedRegistrationExistsInEs = rejectedRegistrationSearchRepository.exists(rejectedRegistration.getId());
        assertThat(rejectedRegistrationExistsInEs).isFalse();

        // Validate the database is empty
        List<RejectedRegistration> rejectedRegistrationList = rejectedRegistrationRepository.findAll();
        assertThat(rejectedRegistrationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchRejectedRegistration() throws Exception {
        // Initialize the database
        rejectedRegistrationRepository.saveAndFlush(rejectedRegistration);
        rejectedRegistrationSearchRepository.save(rejectedRegistration);

        // Search the rejectedRegistration
        restRejectedRegistrationMockMvc.perform(get("/api/_search/rejected-registrations?query=id:" + rejectedRegistration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rejectedRegistration.getId().intValue())))
            .andExpect(jsonPath("$.[*].ePostKeyName").value(hasItem(DEFAULT_E_POST_KEY_NAME.toString())))
            .andExpect(jsonPath("$.[*].ePostKeyValue").value(hasItem(DEFAULT_E_POST_KEY_VALUE.toString())))
            .andExpect(jsonPath("$.[*].noAVS").value(hasItem(DEFAULT_NO_AVS.toString())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
            .andExpect(jsonPath("$.[*].dateDeNaissance").value(hasItem(DEFAULT_DATE_DE_NAISSANCE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RejectedRegistration.class);
        RejectedRegistration rejectedRegistration1 = new RejectedRegistration();
        rejectedRegistration1.setId(1L);
        RejectedRegistration rejectedRegistration2 = new RejectedRegistration();
        rejectedRegistration2.setId(rejectedRegistration1.getId());
        assertThat(rejectedRegistration1).isEqualTo(rejectedRegistration2);
        rejectedRegistration2.setId(2L);
        assertThat(rejectedRegistration1).isNotEqualTo(rejectedRegistration2);
        rejectedRegistration1.setId(null);
        assertThat(rejectedRegistration1).isNotEqualTo(rejectedRegistration2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RejectedRegistrationDTO.class);
        RejectedRegistrationDTO rejectedRegistrationDTO1 = new RejectedRegistrationDTO();
        rejectedRegistrationDTO1.setId(1L);
        RejectedRegistrationDTO rejectedRegistrationDTO2 = new RejectedRegistrationDTO();
        assertThat(rejectedRegistrationDTO1).isNotEqualTo(rejectedRegistrationDTO2);
        rejectedRegistrationDTO2.setId(rejectedRegistrationDTO1.getId());
        assertThat(rejectedRegistrationDTO1).isEqualTo(rejectedRegistrationDTO2);
        rejectedRegistrationDTO2.setId(2L);
        assertThat(rejectedRegistrationDTO1).isNotEqualTo(rejectedRegistrationDTO2);
        rejectedRegistrationDTO1.setId(null);
        assertThat(rejectedRegistrationDTO1).isNotEqualTo(rejectedRegistrationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(rejectedRegistrationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(rejectedRegistrationMapper.fromId(null)).isNull();
    }
}
