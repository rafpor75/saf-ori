package com.saf.web.rest;

import com.saf.SafOriApp;

import com.saf.domain.Facolta;
import com.saf.repository.FacoltaRepository;
import com.saf.service.FacoltaService;
import com.saf.service.dto.FacoltaDTO;
import com.saf.service.mapper.FacoltaMapper;
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
 * Test class for the FacoltaResource REST controller.
 *
 * @see FacoltaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SafOriApp.class)
public class FacoltaResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_MODIFICA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_MODIFICA = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_ABILITATO = false;
    private static final Boolean UPDATED_ABILITATO = true;

    @Autowired
    private FacoltaRepository facoltaRepository;

    @Autowired
    private FacoltaMapper facoltaMapper;

    @Autowired
    private FacoltaService facoltaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFacoltaMockMvc;

    private Facolta facolta;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FacoltaResource facoltaResource = new FacoltaResource(facoltaService);
        this.restFacoltaMockMvc = MockMvcBuilders.standaloneSetup(facoltaResource)
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
    public static Facolta createEntity(EntityManager em) {
        Facolta facolta = new Facolta()
            .nome(DEFAULT_NOME)
            .dataModifica(DEFAULT_DATA_MODIFICA)
            .abilitato(DEFAULT_ABILITATO);
        return facolta;
    }

    @Before
    public void initTest() {
        facolta = createEntity(em);
    }

    @Test
    @Transactional
    public void createFacolta() throws Exception {
        int databaseSizeBeforeCreate = facoltaRepository.findAll().size();

        // Create the Facolta
        FacoltaDTO facoltaDTO = facoltaMapper.toDto(facolta);
        restFacoltaMockMvc.perform(post("/api/facoltas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facoltaDTO)))
            .andExpect(status().isCreated());

        // Validate the Facolta in the database
        List<Facolta> facoltaList = facoltaRepository.findAll();
        assertThat(facoltaList).hasSize(databaseSizeBeforeCreate + 1);
        Facolta testFacolta = facoltaList.get(facoltaList.size() - 1);
        assertThat(testFacolta.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testFacolta.getDataModifica()).isEqualTo(DEFAULT_DATA_MODIFICA);
        assertThat(testFacolta.isAbilitato()).isEqualTo(DEFAULT_ABILITATO);
    }

    @Test
    @Transactional
    public void createFacoltaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = facoltaRepository.findAll().size();

        // Create the Facolta with an existing ID
        facolta.setId(1L);
        FacoltaDTO facoltaDTO = facoltaMapper.toDto(facolta);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFacoltaMockMvc.perform(post("/api/facoltas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facoltaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Facolta in the database
        List<Facolta> facoltaList = facoltaRepository.findAll();
        assertThat(facoltaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = facoltaRepository.findAll().size();
        // set the field null
        facolta.setNome(null);

        // Create the Facolta, which fails.
        FacoltaDTO facoltaDTO = facoltaMapper.toDto(facolta);

        restFacoltaMockMvc.perform(post("/api/facoltas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facoltaDTO)))
            .andExpect(status().isBadRequest());

        List<Facolta> facoltaList = facoltaRepository.findAll();
        assertThat(facoltaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFacoltas() throws Exception {
        // Initialize the database
        facoltaRepository.saveAndFlush(facolta);

        // Get all the facoltaList
        restFacoltaMockMvc.perform(get("/api/facoltas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(facolta.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].dataModifica").value(hasItem(DEFAULT_DATA_MODIFICA.toString())))
            .andExpect(jsonPath("$.[*].abilitato").value(hasItem(DEFAULT_ABILITATO.booleanValue())));
    }

    @Test
    @Transactional
    public void getFacolta() throws Exception {
        // Initialize the database
        facoltaRepository.saveAndFlush(facolta);

        // Get the facolta
        restFacoltaMockMvc.perform(get("/api/facoltas/{id}", facolta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(facolta.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.dataModifica").value(DEFAULT_DATA_MODIFICA.toString()))
            .andExpect(jsonPath("$.abilitato").value(DEFAULT_ABILITATO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFacolta() throws Exception {
        // Get the facolta
        restFacoltaMockMvc.perform(get("/api/facoltas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFacolta() throws Exception {
        // Initialize the database
        facoltaRepository.saveAndFlush(facolta);
        int databaseSizeBeforeUpdate = facoltaRepository.findAll().size();

        // Update the facolta
        Facolta updatedFacolta = facoltaRepository.findOne(facolta.getId());
        // Disconnect from session so that the updates on updatedFacolta are not directly saved in db
        em.detach(updatedFacolta);
        updatedFacolta
            .nome(UPDATED_NOME)
            .dataModifica(UPDATED_DATA_MODIFICA)
            .abilitato(UPDATED_ABILITATO);
        FacoltaDTO facoltaDTO = facoltaMapper.toDto(updatedFacolta);

        restFacoltaMockMvc.perform(put("/api/facoltas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facoltaDTO)))
            .andExpect(status().isOk());

        // Validate the Facolta in the database
        List<Facolta> facoltaList = facoltaRepository.findAll();
        assertThat(facoltaList).hasSize(databaseSizeBeforeUpdate);
        Facolta testFacolta = facoltaList.get(facoltaList.size() - 1);
        assertThat(testFacolta.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testFacolta.getDataModifica()).isEqualTo(UPDATED_DATA_MODIFICA);
        assertThat(testFacolta.isAbilitato()).isEqualTo(UPDATED_ABILITATO);
    }

    @Test
    @Transactional
    public void updateNonExistingFacolta() throws Exception {
        int databaseSizeBeforeUpdate = facoltaRepository.findAll().size();

        // Create the Facolta
        FacoltaDTO facoltaDTO = facoltaMapper.toDto(facolta);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFacoltaMockMvc.perform(put("/api/facoltas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facoltaDTO)))
            .andExpect(status().isCreated());

        // Validate the Facolta in the database
        List<Facolta> facoltaList = facoltaRepository.findAll();
        assertThat(facoltaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFacolta() throws Exception {
        // Initialize the database
        facoltaRepository.saveAndFlush(facolta);
        int databaseSizeBeforeDelete = facoltaRepository.findAll().size();

        // Get the facolta
        restFacoltaMockMvc.perform(delete("/api/facoltas/{id}", facolta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Facolta> facoltaList = facoltaRepository.findAll();
        assertThat(facoltaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Facolta.class);
        Facolta facolta1 = new Facolta();
        facolta1.setId(1L);
        Facolta facolta2 = new Facolta();
        facolta2.setId(facolta1.getId());
        assertThat(facolta1).isEqualTo(facolta2);
        facolta2.setId(2L);
        assertThat(facolta1).isNotEqualTo(facolta2);
        facolta1.setId(null);
        assertThat(facolta1).isNotEqualTo(facolta2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FacoltaDTO.class);
        FacoltaDTO facoltaDTO1 = new FacoltaDTO();
        facoltaDTO1.setId(1L);
        FacoltaDTO facoltaDTO2 = new FacoltaDTO();
        assertThat(facoltaDTO1).isNotEqualTo(facoltaDTO2);
        facoltaDTO2.setId(facoltaDTO1.getId());
        assertThat(facoltaDTO1).isEqualTo(facoltaDTO2);
        facoltaDTO2.setId(2L);
        assertThat(facoltaDTO1).isNotEqualTo(facoltaDTO2);
        facoltaDTO1.setId(null);
        assertThat(facoltaDTO1).isNotEqualTo(facoltaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(facoltaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(facoltaMapper.fromId(null)).isNull();
    }
}
