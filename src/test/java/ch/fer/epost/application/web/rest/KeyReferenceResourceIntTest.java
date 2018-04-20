package ch.fer.epost.application.web.rest;

import ch.fer.epost.application.EPostBoxApp;

import ch.fer.epost.application.domain.KeyReference;
import ch.fer.epost.application.repository.KeyReferenceRepository;
import ch.fer.epost.application.service.KeyReferenceService;
import ch.fer.epost.application.repository.search.KeyReferenceSearchRepository;
import ch.fer.epost.application.service.dto.KeyReferenceDTO;
import ch.fer.epost.application.service.mapper.KeyReferenceMapper;
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

/**
 * Test class for the KeyReferenceResource REST controller.
 *
 * @see KeyReferenceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EPostBoxApp.class)
public class KeyReferenceResourceIntTest {

    private static final String DEFAULT_INTERNAL_KEY = "AAAAAAAAAA";
    private static final String UPDATED_INTERNAL_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_E_POST_KEY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_E_POST_KEY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_E_POST_KEY_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_E_POST_KEY_VALUE = "BBBBBBBBBB";

    @Autowired
    private KeyReferenceRepository keyReferenceRepository;

    @Autowired
    private KeyReferenceMapper keyReferenceMapper;

    @Autowired
    private KeyReferenceService keyReferenceService;

    @Autowired
    private KeyReferenceSearchRepository keyReferenceSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restKeyReferenceMockMvc;

    private KeyReference keyReference;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final KeyReferenceResource keyReferenceResource = new KeyReferenceResource(keyReferenceService);
        this.restKeyReferenceMockMvc = MockMvcBuilders.standaloneSetup(keyReferenceResource)
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
    public static KeyReference createEntity(EntityManager em) {
        KeyReference keyReference = new KeyReference()
            .internalKey(DEFAULT_INTERNAL_KEY)
            .ePostKeyName(DEFAULT_E_POST_KEY_NAME)
            .ePostKeyValue(DEFAULT_E_POST_KEY_VALUE);
        return keyReference;
    }

    @Before
    public void initTest() {
        keyReferenceSearchRepository.deleteAll();
        keyReference = createEntity(em);
    }

    @Test
    @Transactional
    public void createKeyReference() throws Exception {
        int databaseSizeBeforeCreate = keyReferenceRepository.findAll().size();

        // Create the KeyReference
        KeyReferenceDTO keyReferenceDTO = keyReferenceMapper.toDto(keyReference);
        restKeyReferenceMockMvc.perform(post("/api/key-references")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(keyReferenceDTO)))
            .andExpect(status().isCreated());

        // Validate the KeyReference in the database
        List<KeyReference> keyReferenceList = keyReferenceRepository.findAll();
        assertThat(keyReferenceList).hasSize(databaseSizeBeforeCreate + 1);
        KeyReference testKeyReference = keyReferenceList.get(keyReferenceList.size() - 1);
        assertThat(testKeyReference.getInternalKey()).isEqualTo(DEFAULT_INTERNAL_KEY);
        assertThat(testKeyReference.getePostKeyName()).isEqualTo(DEFAULT_E_POST_KEY_NAME);
        assertThat(testKeyReference.getePostKeyValue()).isEqualTo(DEFAULT_E_POST_KEY_VALUE);

        // Validate the KeyReference in Elasticsearch
        KeyReference keyReferenceEs = keyReferenceSearchRepository.findOne(testKeyReference.getId());
        assertThat(keyReferenceEs).isEqualToIgnoringGivenFields(testKeyReference);
    }

    @Test
    @Transactional
    public void createKeyReferenceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = keyReferenceRepository.findAll().size();

        // Create the KeyReference with an existing ID
        keyReference.setId(1L);
        KeyReferenceDTO keyReferenceDTO = keyReferenceMapper.toDto(keyReference);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKeyReferenceMockMvc.perform(post("/api/key-references")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(keyReferenceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the KeyReference in the database
        List<KeyReference> keyReferenceList = keyReferenceRepository.findAll();
        assertThat(keyReferenceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkInternalKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = keyReferenceRepository.findAll().size();
        // set the field null
        keyReference.setInternalKey(null);

        // Create the KeyReference, which fails.
        KeyReferenceDTO keyReferenceDTO = keyReferenceMapper.toDto(keyReference);

        restKeyReferenceMockMvc.perform(post("/api/key-references")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(keyReferenceDTO)))
            .andExpect(status().isBadRequest());

        List<KeyReference> keyReferenceList = keyReferenceRepository.findAll();
        assertThat(keyReferenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkePostKeyNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = keyReferenceRepository.findAll().size();
        // set the field null
        keyReference.setePostKeyName(null);

        // Create the KeyReference, which fails.
        KeyReferenceDTO keyReferenceDTO = keyReferenceMapper.toDto(keyReference);

        restKeyReferenceMockMvc.perform(post("/api/key-references")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(keyReferenceDTO)))
            .andExpect(status().isBadRequest());

        List<KeyReference> keyReferenceList = keyReferenceRepository.findAll();
        assertThat(keyReferenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkePostKeyValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = keyReferenceRepository.findAll().size();
        // set the field null
        keyReference.setePostKeyValue(null);

        // Create the KeyReference, which fails.
        KeyReferenceDTO keyReferenceDTO = keyReferenceMapper.toDto(keyReference);

        restKeyReferenceMockMvc.perform(post("/api/key-references")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(keyReferenceDTO)))
            .andExpect(status().isBadRequest());

        List<KeyReference> keyReferenceList = keyReferenceRepository.findAll();
        assertThat(keyReferenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllKeyReferences() throws Exception {
        // Initialize the database
        keyReferenceRepository.saveAndFlush(keyReference);

        // Get all the keyReferenceList
        restKeyReferenceMockMvc.perform(get("/api/key-references?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(keyReference.getId().intValue())))
            .andExpect(jsonPath("$.[*].internalKey").value(hasItem(DEFAULT_INTERNAL_KEY.toString())))
            .andExpect(jsonPath("$.[*].ePostKeyName").value(hasItem(DEFAULT_E_POST_KEY_NAME.toString())))
            .andExpect(jsonPath("$.[*].ePostKeyValue").value(hasItem(DEFAULT_E_POST_KEY_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getKeyReference() throws Exception {
        // Initialize the database
        keyReferenceRepository.saveAndFlush(keyReference);

        // Get the keyReference
        restKeyReferenceMockMvc.perform(get("/api/key-references/{id}", keyReference.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(keyReference.getId().intValue()))
            .andExpect(jsonPath("$.internalKey").value(DEFAULT_INTERNAL_KEY.toString()))
            .andExpect(jsonPath("$.ePostKeyName").value(DEFAULT_E_POST_KEY_NAME.toString()))
            .andExpect(jsonPath("$.ePostKeyValue").value(DEFAULT_E_POST_KEY_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingKeyReference() throws Exception {
        // Get the keyReference
        restKeyReferenceMockMvc.perform(get("/api/key-references/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKeyReference() throws Exception {
        // Initialize the database
        keyReferenceRepository.saveAndFlush(keyReference);
        keyReferenceSearchRepository.save(keyReference);
        int databaseSizeBeforeUpdate = keyReferenceRepository.findAll().size();

        // Update the keyReference
        KeyReference updatedKeyReference = keyReferenceRepository.findOne(keyReference.getId());
        // Disconnect from session so that the updates on updatedKeyReference are not directly saved in db
        em.detach(updatedKeyReference);
        updatedKeyReference
            .internalKey(UPDATED_INTERNAL_KEY)
            .ePostKeyName(UPDATED_E_POST_KEY_NAME)
            .ePostKeyValue(UPDATED_E_POST_KEY_VALUE);
        KeyReferenceDTO keyReferenceDTO = keyReferenceMapper.toDto(updatedKeyReference);

        restKeyReferenceMockMvc.perform(put("/api/key-references")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(keyReferenceDTO)))
            .andExpect(status().isOk());

        // Validate the KeyReference in the database
        List<KeyReference> keyReferenceList = keyReferenceRepository.findAll();
        assertThat(keyReferenceList).hasSize(databaseSizeBeforeUpdate);
        KeyReference testKeyReference = keyReferenceList.get(keyReferenceList.size() - 1);
        assertThat(testKeyReference.getInternalKey()).isEqualTo(UPDATED_INTERNAL_KEY);
        assertThat(testKeyReference.getePostKeyName()).isEqualTo(UPDATED_E_POST_KEY_NAME);
        assertThat(testKeyReference.getePostKeyValue()).isEqualTo(UPDATED_E_POST_KEY_VALUE);

        // Validate the KeyReference in Elasticsearch
        KeyReference keyReferenceEs = keyReferenceSearchRepository.findOne(testKeyReference.getId());
        assertThat(keyReferenceEs).isEqualToIgnoringGivenFields(testKeyReference);
    }

    @Test
    @Transactional
    public void updateNonExistingKeyReference() throws Exception {
        int databaseSizeBeforeUpdate = keyReferenceRepository.findAll().size();

        // Create the KeyReference
        KeyReferenceDTO keyReferenceDTO = keyReferenceMapper.toDto(keyReference);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restKeyReferenceMockMvc.perform(put("/api/key-references")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(keyReferenceDTO)))
            .andExpect(status().isCreated());

        // Validate the KeyReference in the database
        List<KeyReference> keyReferenceList = keyReferenceRepository.findAll();
        assertThat(keyReferenceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteKeyReference() throws Exception {
        // Initialize the database
        keyReferenceRepository.saveAndFlush(keyReference);
        keyReferenceSearchRepository.save(keyReference);
        int databaseSizeBeforeDelete = keyReferenceRepository.findAll().size();

        // Get the keyReference
        restKeyReferenceMockMvc.perform(delete("/api/key-references/{id}", keyReference.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean keyReferenceExistsInEs = keyReferenceSearchRepository.exists(keyReference.getId());
        assertThat(keyReferenceExistsInEs).isFalse();

        // Validate the database is empty
        List<KeyReference> keyReferenceList = keyReferenceRepository.findAll();
        assertThat(keyReferenceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchKeyReference() throws Exception {
        // Initialize the database
        keyReferenceRepository.saveAndFlush(keyReference);
        keyReferenceSearchRepository.save(keyReference);

        // Search the keyReference
        restKeyReferenceMockMvc.perform(get("/api/_search/key-references?query=id:" + keyReference.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(keyReference.getId().intValue())))
            .andExpect(jsonPath("$.[*].internalKey").value(hasItem(DEFAULT_INTERNAL_KEY.toString())))
            .andExpect(jsonPath("$.[*].ePostKeyName").value(hasItem(DEFAULT_E_POST_KEY_NAME.toString())))
            .andExpect(jsonPath("$.[*].ePostKeyValue").value(hasItem(DEFAULT_E_POST_KEY_VALUE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KeyReference.class);
        KeyReference keyReference1 = new KeyReference();
        keyReference1.setId(1L);
        KeyReference keyReference2 = new KeyReference();
        keyReference2.setId(keyReference1.getId());
        assertThat(keyReference1).isEqualTo(keyReference2);
        keyReference2.setId(2L);
        assertThat(keyReference1).isNotEqualTo(keyReference2);
        keyReference1.setId(null);
        assertThat(keyReference1).isNotEqualTo(keyReference2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(KeyReferenceDTO.class);
        KeyReferenceDTO keyReferenceDTO1 = new KeyReferenceDTO();
        keyReferenceDTO1.setId(1L);
        KeyReferenceDTO keyReferenceDTO2 = new KeyReferenceDTO();
        assertThat(keyReferenceDTO1).isNotEqualTo(keyReferenceDTO2);
        keyReferenceDTO2.setId(keyReferenceDTO1.getId());
        assertThat(keyReferenceDTO1).isEqualTo(keyReferenceDTO2);
        keyReferenceDTO2.setId(2L);
        assertThat(keyReferenceDTO1).isNotEqualTo(keyReferenceDTO2);
        keyReferenceDTO1.setId(null);
        assertThat(keyReferenceDTO1).isNotEqualTo(keyReferenceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(keyReferenceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(keyReferenceMapper.fromId(null)).isNull();
    }
}
