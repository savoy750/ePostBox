package ch.fer.epost.application.web.rest;

import ch.fer.epost.application.EPostBoxApp;

import ch.fer.epost.application.domain.DataReference;
import ch.fer.epost.application.repository.DataReferenceRepository;
import ch.fer.epost.application.service.DataReferenceService;
import ch.fer.epost.application.repository.search.DataReferenceSearchRepository;
import ch.fer.epost.application.service.dto.DataReferenceDTO;
import ch.fer.epost.application.service.mapper.DataReferenceMapper;
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
 * Test class for the DataReferenceResource REST controller.
 *
 * @see DataReferenceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EPostBoxApp.class)
public class DataReferenceResourceIntTest {

    private static final String DEFAULT_INTERNAL_KEY = "AAAAAAAAAA";
    private static final String UPDATED_INTERNAL_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_NO_AVS = "AAAAAAAAAA";
    private static final String UPDATED_NO_AVS = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_DE_NAISSANCE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_DE_NAISSANCE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private DataReferenceRepository dataReferenceRepository;

    @Autowired
    private DataReferenceMapper dataReferenceMapper;

    @Autowired
    private DataReferenceService dataReferenceService;

    @Autowired
    private DataReferenceSearchRepository dataReferenceSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDataReferenceMockMvc;

    private DataReference dataReference;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DataReferenceResource dataReferenceResource = new DataReferenceResource(dataReferenceService);
        this.restDataReferenceMockMvc = MockMvcBuilders.standaloneSetup(dataReferenceResource)
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
    public static DataReference createEntity(EntityManager em) {
        DataReference dataReference = new DataReference()
            .internalKey(DEFAULT_INTERNAL_KEY)
            .noAVS(DEFAULT_NO_AVS)
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .dateDeNaissance(DEFAULT_DATE_DE_NAISSANCE);
        return dataReference;
    }

    @Before
    public void initTest() {
        dataReferenceSearchRepository.deleteAll();
        dataReference = createEntity(em);
    }

    @Test
    @Transactional
    public void createDataReference() throws Exception {
        int databaseSizeBeforeCreate = dataReferenceRepository.findAll().size();

        // Create the DataReference
        DataReferenceDTO dataReferenceDTO = dataReferenceMapper.toDto(dataReference);
        restDataReferenceMockMvc.perform(post("/api/data-references")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataReferenceDTO)))
            .andExpect(status().isCreated());

        // Validate the DataReference in the database
        List<DataReference> dataReferenceList = dataReferenceRepository.findAll();
        assertThat(dataReferenceList).hasSize(databaseSizeBeforeCreate + 1);
        DataReference testDataReference = dataReferenceList.get(dataReferenceList.size() - 1);
        assertThat(testDataReference.getInternalKey()).isEqualTo(DEFAULT_INTERNAL_KEY);
        assertThat(testDataReference.getNoAVS()).isEqualTo(DEFAULT_NO_AVS);
        assertThat(testDataReference.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testDataReference.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testDataReference.getDateDeNaissance()).isEqualTo(DEFAULT_DATE_DE_NAISSANCE);

        // Validate the DataReference in Elasticsearch
        DataReference dataReferenceEs = dataReferenceSearchRepository.findOne(testDataReference.getId());
        assertThat(dataReferenceEs).isEqualToIgnoringGivenFields(testDataReference);
    }

    @Test
    @Transactional
    public void createDataReferenceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dataReferenceRepository.findAll().size();

        // Create the DataReference with an existing ID
        dataReference.setId(1L);
        DataReferenceDTO dataReferenceDTO = dataReferenceMapper.toDto(dataReference);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDataReferenceMockMvc.perform(post("/api/data-references")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataReferenceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DataReference in the database
        List<DataReference> dataReferenceList = dataReferenceRepository.findAll();
        assertThat(dataReferenceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDataReferences() throws Exception {
        // Initialize the database
        dataReferenceRepository.saveAndFlush(dataReference);

        // Get all the dataReferenceList
        restDataReferenceMockMvc.perform(get("/api/data-references?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataReference.getId().intValue())))
            .andExpect(jsonPath("$.[*].internalKey").value(hasItem(DEFAULT_INTERNAL_KEY.toString())))
            .andExpect(jsonPath("$.[*].noAVS").value(hasItem(DEFAULT_NO_AVS.toString())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
            .andExpect(jsonPath("$.[*].dateDeNaissance").value(hasItem(DEFAULT_DATE_DE_NAISSANCE.toString())));
    }

    @Test
    @Transactional
    public void getDataReference() throws Exception {
        // Initialize the database
        dataReferenceRepository.saveAndFlush(dataReference);

        // Get the dataReference
        restDataReferenceMockMvc.perform(get("/api/data-references/{id}", dataReference.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dataReference.getId().intValue()))
            .andExpect(jsonPath("$.internalKey").value(DEFAULT_INTERNAL_KEY.toString()))
            .andExpect(jsonPath("$.noAVS").value(DEFAULT_NO_AVS.toString()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM.toString()))
            .andExpect(jsonPath("$.dateDeNaissance").value(DEFAULT_DATE_DE_NAISSANCE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDataReference() throws Exception {
        // Get the dataReference
        restDataReferenceMockMvc.perform(get("/api/data-references/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDataReference() throws Exception {
        // Initialize the database
        dataReferenceRepository.saveAndFlush(dataReference);
        dataReferenceSearchRepository.save(dataReference);
        int databaseSizeBeforeUpdate = dataReferenceRepository.findAll().size();

        // Update the dataReference
        DataReference updatedDataReference = dataReferenceRepository.findOne(dataReference.getId());
        // Disconnect from session so that the updates on updatedDataReference are not directly saved in db
        em.detach(updatedDataReference);
        updatedDataReference
            .internalKey(UPDATED_INTERNAL_KEY)
            .noAVS(UPDATED_NO_AVS)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .dateDeNaissance(UPDATED_DATE_DE_NAISSANCE);
        DataReferenceDTO dataReferenceDTO = dataReferenceMapper.toDto(updatedDataReference);

        restDataReferenceMockMvc.perform(put("/api/data-references")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataReferenceDTO)))
            .andExpect(status().isOk());

        // Validate the DataReference in the database
        List<DataReference> dataReferenceList = dataReferenceRepository.findAll();
        assertThat(dataReferenceList).hasSize(databaseSizeBeforeUpdate);
        DataReference testDataReference = dataReferenceList.get(dataReferenceList.size() - 1);
        assertThat(testDataReference.getInternalKey()).isEqualTo(UPDATED_INTERNAL_KEY);
        assertThat(testDataReference.getNoAVS()).isEqualTo(UPDATED_NO_AVS);
        assertThat(testDataReference.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testDataReference.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testDataReference.getDateDeNaissance()).isEqualTo(UPDATED_DATE_DE_NAISSANCE);

        // Validate the DataReference in Elasticsearch
        DataReference dataReferenceEs = dataReferenceSearchRepository.findOne(testDataReference.getId());
        assertThat(dataReferenceEs).isEqualToIgnoringGivenFields(testDataReference);
    }

    @Test
    @Transactional
    public void updateNonExistingDataReference() throws Exception {
        int databaseSizeBeforeUpdate = dataReferenceRepository.findAll().size();

        // Create the DataReference
        DataReferenceDTO dataReferenceDTO = dataReferenceMapper.toDto(dataReference);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDataReferenceMockMvc.perform(put("/api/data-references")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataReferenceDTO)))
            .andExpect(status().isCreated());

        // Validate the DataReference in the database
        List<DataReference> dataReferenceList = dataReferenceRepository.findAll();
        assertThat(dataReferenceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDataReference() throws Exception {
        // Initialize the database
        dataReferenceRepository.saveAndFlush(dataReference);
        dataReferenceSearchRepository.save(dataReference);
        int databaseSizeBeforeDelete = dataReferenceRepository.findAll().size();

        // Get the dataReference
        restDataReferenceMockMvc.perform(delete("/api/data-references/{id}", dataReference.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean dataReferenceExistsInEs = dataReferenceSearchRepository.exists(dataReference.getId());
        assertThat(dataReferenceExistsInEs).isFalse();

        // Validate the database is empty
        List<DataReference> dataReferenceList = dataReferenceRepository.findAll();
        assertThat(dataReferenceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDataReference() throws Exception {
        // Initialize the database
        dataReferenceRepository.saveAndFlush(dataReference);
        dataReferenceSearchRepository.save(dataReference);

        // Search the dataReference
        restDataReferenceMockMvc.perform(get("/api/_search/data-references?query=id:" + dataReference.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataReference.getId().intValue())))
            .andExpect(jsonPath("$.[*].internalKey").value(hasItem(DEFAULT_INTERNAL_KEY.toString())))
            .andExpect(jsonPath("$.[*].noAVS").value(hasItem(DEFAULT_NO_AVS.toString())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
            .andExpect(jsonPath("$.[*].dateDeNaissance").value(hasItem(DEFAULT_DATE_DE_NAISSANCE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataReference.class);
        DataReference dataReference1 = new DataReference();
        dataReference1.setId(1L);
        DataReference dataReference2 = new DataReference();
        dataReference2.setId(dataReference1.getId());
        assertThat(dataReference1).isEqualTo(dataReference2);
        dataReference2.setId(2L);
        assertThat(dataReference1).isNotEqualTo(dataReference2);
        dataReference1.setId(null);
        assertThat(dataReference1).isNotEqualTo(dataReference2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataReferenceDTO.class);
        DataReferenceDTO dataReferenceDTO1 = new DataReferenceDTO();
        dataReferenceDTO1.setId(1L);
        DataReferenceDTO dataReferenceDTO2 = new DataReferenceDTO();
        assertThat(dataReferenceDTO1).isNotEqualTo(dataReferenceDTO2);
        dataReferenceDTO2.setId(dataReferenceDTO1.getId());
        assertThat(dataReferenceDTO1).isEqualTo(dataReferenceDTO2);
        dataReferenceDTO2.setId(2L);
        assertThat(dataReferenceDTO1).isNotEqualTo(dataReferenceDTO2);
        dataReferenceDTO1.setId(null);
        assertThat(dataReferenceDTO1).isNotEqualTo(dataReferenceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(dataReferenceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(dataReferenceMapper.fromId(null)).isNull();
    }
}
