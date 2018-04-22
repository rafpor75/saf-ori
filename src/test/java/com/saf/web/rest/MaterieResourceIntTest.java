package com.saf.web.rest;

import com.saf.SafOriApp;

import com.saf.domain.Materie;
import com.saf.repository.MaterieRepository;
import com.saf.service.MaterieService;
import com.saf.service.dto.MaterieDTO;
import com.saf.service.mapper.MaterieMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.saf.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MaterieResource REST controller.
 *
 * @see MaterieResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SafOriApp.class)
public class MaterieResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Integer DEFAULT_CFU = 1;
    private static final Integer UPDATED_CFU = 2;

    private static final Boolean DEFAULT_ABILITATO = false;
    private static final Boolean UPDATED_ABILITATO = true;

    private static final LocalDate DEFAULT_DATA_MODIFICA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_MODIFICA = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private MaterieRepository materieRepository;

    @Autowired
    private MaterieMapper materieMapper;

    @Autowired
    private MaterieService materieService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMaterieMockMvc;

    private Materie materie;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MaterieResource materieResource = new MaterieResource(materieService);
        this.restMaterieMockMvc = MockMvcBuilders.standaloneSetup(materieResource)
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
    public static Materie createEntity(EntityManager em) {
        Materie materie = new Materie()
            .nome(DEFAULT_NOME)
            .cfu(DEFAULT_CFU)
            .abilitato(DEFAULT_ABILITATO)
            .dataModifica(DEFAULT_DATA_MODIFICA);
        return materie;
    }

    @Before
    public void initTest() {
        materie = createEntity(em);
    }

    @Test
    @Transactional
    public void createMaterie() throws Exception {
        int databaseSizeBeforeCreate = materieRepository.findAll().size();

        // Create the Materie
        MaterieDTO materieDTO = materieMapper.toDto(materie);
        restMaterieMockMvc.perform(post("/api/materies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(materieDTO)))
            .andExpect(status().isCreated());

        // Validate the Materie in the database
        List<Materie> materieList = materieRepository.findAll();
        assertThat(materieList).hasSize(databaseSizeBeforeCreate + 1);
        Materie testMaterie = materieList.get(materieList.size() - 1);
        assertThat(testMaterie.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testMaterie.getCfu()).isEqualTo(DEFAULT_CFU);
        assertThat(testMaterie.isAbilitato()).isEqualTo(DEFAULT_ABILITATO);
        assertThat(testMaterie.getDataModifica()).isEqualTo(DEFAULT_DATA_MODIFICA);
    }

    @Test
    @Transactional
    public void createMaterieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = materieRepository.findAll().size();

        // Create the Materie with an existing ID
        materie.setId(1L);
        MaterieDTO materieDTO = materieMapper.toDto(materie);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMaterieMockMvc.perform(post("/api/materies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(materieDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Materie in the database
        List<Materie> materieList = materieRepository.findAll();
        assertThat(materieList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = materieRepository.findAll().size();
        // set the field null
        materie.setNome(null);

        // Create the Materie, which fails.
        MaterieDTO materieDTO = materieMapper.toDto(materie);

        restMaterieMockMvc.perform(post("/api/materies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(materieDTO)))
            .andExpect(status().isBadRequest());

        List<Materie> materieList = materieRepository.findAll();
        assertThat(materieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMateries() throws Exception {
        // Initialize the database
        materieRepository.saveAndFlush(materie);

        // Get all the materieList
        restMaterieMockMvc.perform(get("/api/materies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(materie.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].cfu").value(hasItem(DEFAULT_CFU)))
            .andExpect(jsonPath("$.[*].abilitato").value(hasItem(DEFAULT_ABILITATO.booleanValue())))
            .andExpect(jsonPath("$.[*].dataModifica").value(hasItem(DEFAULT_DATA_MODIFICA.toString())));
    }

    @Test
    @Transactional
    public void getMaterie() throws Exception {
        // Initialize the database
        materieRepository.saveAndFlush(materie);

        // Get the materie
        restMaterieMockMvc.perform(get("/api/materies/{id}", materie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(materie.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.cfu").value(DEFAULT_CFU))
            .andExpect(jsonPath("$.abilitato").value(DEFAULT_ABILITATO.booleanValue()))
            .andExpect(jsonPath("$.dataModifica").value(DEFAULT_DATA_MODIFICA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMaterie() throws Exception {
        // Get the materie
        restMaterieMockMvc.perform(get("/api/materies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMaterie() throws Exception {
        // Initialize the database
        materieRepository.saveAndFlush(materie);
        int databaseSizeBeforeUpdate = materieRepository.findAll().size();

        // Update the materie
        Materie updatedMaterie = materieRepository.findOne(materie.getId());
        // Disconnect from session so that the updates on updatedMaterie are not directly saved in db
        em.detach(updatedMaterie);
        updatedMaterie
            .nome(UPDATED_NOME)
            .cfu(UPDATED_CFU)
            .abilitato(UPDATED_ABILITATO)
            .dataModifica(UPDATED_DATA_MODIFICA);
        MaterieDTO materieDTO = materieMapper.toDto(updatedMaterie);

        restMaterieMockMvc.perform(put("/api/materies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(materieDTO)))
            .andExpect(status().isOk());

        // Validate the Materie in the database
        List<Materie> materieList = materieRepository.findAll();
        assertThat(materieList).hasSize(databaseSizeBeforeUpdate);
        Materie testMaterie = materieList.get(materieList.size() - 1);
        assertThat(testMaterie.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testMaterie.getCfu()).isEqualTo(UPDATED_CFU);
        assertThat(testMaterie.isAbilitato()).isEqualTo(UPDATED_ABILITATO);
        assertThat(testMaterie.getDataModifica()).isEqualTo(UPDATED_DATA_MODIFICA);
    }

    @Test
    @Transactional
    public void updateNonExistingMaterie() throws Exception {
        int databaseSizeBeforeUpdate = materieRepository.findAll().size();

        // Create the Materie
        MaterieDTO materieDTO = materieMapper.toDto(materie);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMaterieMockMvc.perform(put("/api/materies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(materieDTO)))
            .andExpect(status().isCreated());

        // Validate the Materie in the database
        List<Materie> materieList = materieRepository.findAll();
        assertThat(materieList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMaterie() throws Exception {
        // Initialize the database
        materieRepository.saveAndFlush(materie);
        int databaseSizeBeforeDelete = materieRepository.findAll().size();

        // Get the materie
        restMaterieMockMvc.perform(delete("/api/materies/{id}", materie.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Materie> materieList = materieRepository.findAll();
        assertThat(materieList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Materie.class);
        Materie materie1 = new Materie();
        materie1.setId(1L);
        Materie materie2 = new Materie();
        materie2.setId(materie1.getId());
        assertThat(materie1).isEqualTo(materie2);
        materie2.setId(2L);
        assertThat(materie1).isNotEqualTo(materie2);
        materie1.setId(null);
        assertThat(materie1).isNotEqualTo(materie2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MaterieDTO.class);
        MaterieDTO materieDTO1 = new MaterieDTO();
        materieDTO1.setId(1L);
        MaterieDTO materieDTO2 = new MaterieDTO();
        assertThat(materieDTO1).isNotEqualTo(materieDTO2);
        materieDTO2.setId(materieDTO1.getId());
        assertThat(materieDTO1).isEqualTo(materieDTO2);
        materieDTO2.setId(2L);
        assertThat(materieDTO1).isNotEqualTo(materieDTO2);
        materieDTO1.setId(null);
        assertThat(materieDTO1).isNotEqualTo(materieDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(materieMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(materieMapper.fromId(null)).isNull();
    }
}
