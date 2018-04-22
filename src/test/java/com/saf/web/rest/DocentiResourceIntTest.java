package com.saf.web.rest;

import com.saf.SafOriApp;

import com.saf.domain.Docenti;
import com.saf.repository.DocentiRepository;
import com.saf.service.DocentiService;
import com.saf.service.dto.DocentiDTO;
import com.saf.service.mapper.DocentiMapper;
import com.saf.web.rest.errors.ExceptionTranslator;

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

import static com.saf.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DocentiResource REST controller.
 *
 * @see DocentiResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SafOriApp.class)
public class DocentiResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_COGNOME = "AAAAAAAAAA";
    private static final String UPDATED_COGNOME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ABILITATO = false;
    private static final Boolean UPDATED_ABILITATO = true;

    @Autowired
    private DocentiRepository docentiRepository;

    @Autowired
    private DocentiMapper docentiMapper;

    @Autowired
    private DocentiService docentiService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDocentiMockMvc;

    private Docenti docenti;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DocentiResource docentiResource = new DocentiResource(docentiService);
        this.restDocentiMockMvc = MockMvcBuilders.standaloneSetup(docentiResource)
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
    public static Docenti createEntity(EntityManager em) {
        Docenti docenti = new Docenti()
            .nome(DEFAULT_NOME)
            .cognome(DEFAULT_COGNOME)
            .email(DEFAULT_EMAIL)
            .abilitato(DEFAULT_ABILITATO);
        return docenti;
    }

    @Before
    public void initTest() {
        docenti = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocenti() throws Exception {
        int databaseSizeBeforeCreate = docentiRepository.findAll().size();

        // Create the Docenti
        DocentiDTO docentiDTO = docentiMapper.toDto(docenti);
        restDocentiMockMvc.perform(post("/api/docentis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(docentiDTO)))
            .andExpect(status().isCreated());

        // Validate the Docenti in the database
        List<Docenti> docentiList = docentiRepository.findAll();
        assertThat(docentiList).hasSize(databaseSizeBeforeCreate + 1);
        Docenti testDocenti = docentiList.get(docentiList.size() - 1);
        assertThat(testDocenti.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testDocenti.getCognome()).isEqualTo(DEFAULT_COGNOME);
        assertThat(testDocenti.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testDocenti.isAbilitato()).isEqualTo(DEFAULT_ABILITATO);
    }

    @Test
    @Transactional
    public void createDocentiWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = docentiRepository.findAll().size();

        // Create the Docenti with an existing ID
        docenti.setId(1L);
        DocentiDTO docentiDTO = docentiMapper.toDto(docenti);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocentiMockMvc.perform(post("/api/docentis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(docentiDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Docenti in the database
        List<Docenti> docentiList = docentiRepository.findAll();
        assertThat(docentiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = docentiRepository.findAll().size();
        // set the field null
        docenti.setNome(null);

        // Create the Docenti, which fails.
        DocentiDTO docentiDTO = docentiMapper.toDto(docenti);

        restDocentiMockMvc.perform(post("/api/docentis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(docentiDTO)))
            .andExpect(status().isBadRequest());

        List<Docenti> docentiList = docentiRepository.findAll();
        assertThat(docentiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCognomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = docentiRepository.findAll().size();
        // set the field null
        docenti.setCognome(null);

        // Create the Docenti, which fails.
        DocentiDTO docentiDTO = docentiMapper.toDto(docenti);

        restDocentiMockMvc.perform(post("/api/docentis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(docentiDTO)))
            .andExpect(status().isBadRequest());

        List<Docenti> docentiList = docentiRepository.findAll();
        assertThat(docentiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDocentis() throws Exception {
        // Initialize the database
        docentiRepository.saveAndFlush(docenti);

        // Get all the docentiList
        restDocentiMockMvc.perform(get("/api/docentis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(docenti.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].cognome").value(hasItem(DEFAULT_COGNOME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].abilitato").value(hasItem(DEFAULT_ABILITATO.booleanValue())));
    }

    @Test
    @Transactional
    public void getDocenti() throws Exception {
        // Initialize the database
        docentiRepository.saveAndFlush(docenti);

        // Get the docenti
        restDocentiMockMvc.perform(get("/api/docentis/{id}", docenti.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(docenti.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.cognome").value(DEFAULT_COGNOME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.abilitato").value(DEFAULT_ABILITATO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDocenti() throws Exception {
        // Get the docenti
        restDocentiMockMvc.perform(get("/api/docentis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocenti() throws Exception {
        // Initialize the database
        docentiRepository.saveAndFlush(docenti);
        int databaseSizeBeforeUpdate = docentiRepository.findAll().size();

        // Update the docenti
        Docenti updatedDocenti = docentiRepository.findOne(docenti.getId());
        // Disconnect from session so that the updates on updatedDocenti are not directly saved in db
        em.detach(updatedDocenti);
        updatedDocenti
            .nome(UPDATED_NOME)
            .cognome(UPDATED_COGNOME)
            .email(UPDATED_EMAIL)
            .abilitato(UPDATED_ABILITATO);
        DocentiDTO docentiDTO = docentiMapper.toDto(updatedDocenti);

        restDocentiMockMvc.perform(put("/api/docentis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(docentiDTO)))
            .andExpect(status().isOk());

        // Validate the Docenti in the database
        List<Docenti> docentiList = docentiRepository.findAll();
        assertThat(docentiList).hasSize(databaseSizeBeforeUpdate);
        Docenti testDocenti = docentiList.get(docentiList.size() - 1);
        assertThat(testDocenti.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testDocenti.getCognome()).isEqualTo(UPDATED_COGNOME);
        assertThat(testDocenti.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDocenti.isAbilitato()).isEqualTo(UPDATED_ABILITATO);
    }

    @Test
    @Transactional
    public void updateNonExistingDocenti() throws Exception {
        int databaseSizeBeforeUpdate = docentiRepository.findAll().size();

        // Create the Docenti
        DocentiDTO docentiDTO = docentiMapper.toDto(docenti);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDocentiMockMvc.perform(put("/api/docentis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(docentiDTO)))
            .andExpect(status().isCreated());

        // Validate the Docenti in the database
        List<Docenti> docentiList = docentiRepository.findAll();
        assertThat(docentiList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDocenti() throws Exception {
        // Initialize the database
        docentiRepository.saveAndFlush(docenti);
        int databaseSizeBeforeDelete = docentiRepository.findAll().size();

        // Get the docenti
        restDocentiMockMvc.perform(delete("/api/docentis/{id}", docenti.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Docenti> docentiList = docentiRepository.findAll();
        assertThat(docentiList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Docenti.class);
        Docenti docenti1 = new Docenti();
        docenti1.setId(1L);
        Docenti docenti2 = new Docenti();
        docenti2.setId(docenti1.getId());
        assertThat(docenti1).isEqualTo(docenti2);
        docenti2.setId(2L);
        assertThat(docenti1).isNotEqualTo(docenti2);
        docenti1.setId(null);
        assertThat(docenti1).isNotEqualTo(docenti2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocentiDTO.class);
        DocentiDTO docentiDTO1 = new DocentiDTO();
        docentiDTO1.setId(1L);
        DocentiDTO docentiDTO2 = new DocentiDTO();
        assertThat(docentiDTO1).isNotEqualTo(docentiDTO2);
        docentiDTO2.setId(docentiDTO1.getId());
        assertThat(docentiDTO1).isEqualTo(docentiDTO2);
        docentiDTO2.setId(2L);
        assertThat(docentiDTO1).isNotEqualTo(docentiDTO2);
        docentiDTO1.setId(null);
        assertThat(docentiDTO1).isNotEqualTo(docentiDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(docentiMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(docentiMapper.fromId(null)).isNull();
    }
}
