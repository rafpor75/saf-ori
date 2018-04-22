package com.saf.web.rest;

import com.saf.SafOriApp;

import com.saf.domain.AnnoAccademico;
import com.saf.repository.AnnoAccademicoRepository;
import com.saf.service.AnnoAccademicoService;
import com.saf.service.dto.AnnoAccademicoDTO;
import com.saf.service.mapper.AnnoAccademicoMapper;
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
 * Test class for the AnnoAccademicoResource REST controller.
 *
 * @see AnnoAccademicoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SafOriApp.class)
public class AnnoAccademicoResourceIntTest {

    private static final String DEFAULT_DESCRIZIONE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIZIONE = "BBBBBBBBBB";

    @Autowired
    private AnnoAccademicoRepository annoAccademicoRepository;

    @Autowired
    private AnnoAccademicoMapper annoAccademicoMapper;

    @Autowired
    private AnnoAccademicoService annoAccademicoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAnnoAccademicoMockMvc;

    private AnnoAccademico annoAccademico;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnnoAccademicoResource annoAccademicoResource = new AnnoAccademicoResource(annoAccademicoService);
        this.restAnnoAccademicoMockMvc = MockMvcBuilders.standaloneSetup(annoAccademicoResource)
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
    public static AnnoAccademico createEntity(EntityManager em) {
        AnnoAccademico annoAccademico = new AnnoAccademico()
            .descrizione(DEFAULT_DESCRIZIONE);
        return annoAccademico;
    }

    @Before
    public void initTest() {
        annoAccademico = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnnoAccademico() throws Exception {
        int databaseSizeBeforeCreate = annoAccademicoRepository.findAll().size();

        // Create the AnnoAccademico
        AnnoAccademicoDTO annoAccademicoDTO = annoAccademicoMapper.toDto(annoAccademico);
        restAnnoAccademicoMockMvc.perform(post("/api/anno-accademicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(annoAccademicoDTO)))
            .andExpect(status().isCreated());

        // Validate the AnnoAccademico in the database
        List<AnnoAccademico> annoAccademicoList = annoAccademicoRepository.findAll();
        assertThat(annoAccademicoList).hasSize(databaseSizeBeforeCreate + 1);
        AnnoAccademico testAnnoAccademico = annoAccademicoList.get(annoAccademicoList.size() - 1);
        assertThat(testAnnoAccademico.getDescrizione()).isEqualTo(DEFAULT_DESCRIZIONE);
    }

    @Test
    @Transactional
    public void createAnnoAccademicoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = annoAccademicoRepository.findAll().size();

        // Create the AnnoAccademico with an existing ID
        annoAccademico.setId(1L);
        AnnoAccademicoDTO annoAccademicoDTO = annoAccademicoMapper.toDto(annoAccademico);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnnoAccademicoMockMvc.perform(post("/api/anno-accademicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(annoAccademicoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AnnoAccademico in the database
        List<AnnoAccademico> annoAccademicoList = annoAccademicoRepository.findAll();
        assertThat(annoAccademicoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescrizioneIsRequired() throws Exception {
        int databaseSizeBeforeTest = annoAccademicoRepository.findAll().size();
        // set the field null
        annoAccademico.setDescrizione(null);

        // Create the AnnoAccademico, which fails.
        AnnoAccademicoDTO annoAccademicoDTO = annoAccademicoMapper.toDto(annoAccademico);

        restAnnoAccademicoMockMvc.perform(post("/api/anno-accademicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(annoAccademicoDTO)))
            .andExpect(status().isBadRequest());

        List<AnnoAccademico> annoAccademicoList = annoAccademicoRepository.findAll();
        assertThat(annoAccademicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAnnoAccademicos() throws Exception {
        // Initialize the database
        annoAccademicoRepository.saveAndFlush(annoAccademico);

        // Get all the annoAccademicoList
        restAnnoAccademicoMockMvc.perform(get("/api/anno-accademicos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(annoAccademico.getId().intValue())))
            .andExpect(jsonPath("$.[*].descrizione").value(hasItem(DEFAULT_DESCRIZIONE.toString())));
    }

    @Test
    @Transactional
    public void getAnnoAccademico() throws Exception {
        // Initialize the database
        annoAccademicoRepository.saveAndFlush(annoAccademico);

        // Get the annoAccademico
        restAnnoAccademicoMockMvc.perform(get("/api/anno-accademicos/{id}", annoAccademico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(annoAccademico.getId().intValue()))
            .andExpect(jsonPath("$.descrizione").value(DEFAULT_DESCRIZIONE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAnnoAccademico() throws Exception {
        // Get the annoAccademico
        restAnnoAccademicoMockMvc.perform(get("/api/anno-accademicos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnnoAccademico() throws Exception {
        // Initialize the database
        annoAccademicoRepository.saveAndFlush(annoAccademico);
        int databaseSizeBeforeUpdate = annoAccademicoRepository.findAll().size();

        // Update the annoAccademico
        AnnoAccademico updatedAnnoAccademico = annoAccademicoRepository.findOne(annoAccademico.getId());
        // Disconnect from session so that the updates on updatedAnnoAccademico are not directly saved in db
        em.detach(updatedAnnoAccademico);
        updatedAnnoAccademico
            .descrizione(UPDATED_DESCRIZIONE);
        AnnoAccademicoDTO annoAccademicoDTO = annoAccademicoMapper.toDto(updatedAnnoAccademico);

        restAnnoAccademicoMockMvc.perform(put("/api/anno-accademicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(annoAccademicoDTO)))
            .andExpect(status().isOk());

        // Validate the AnnoAccademico in the database
        List<AnnoAccademico> annoAccademicoList = annoAccademicoRepository.findAll();
        assertThat(annoAccademicoList).hasSize(databaseSizeBeforeUpdate);
        AnnoAccademico testAnnoAccademico = annoAccademicoList.get(annoAccademicoList.size() - 1);
        assertThat(testAnnoAccademico.getDescrizione()).isEqualTo(UPDATED_DESCRIZIONE);
    }

    @Test
    @Transactional
    public void updateNonExistingAnnoAccademico() throws Exception {
        int databaseSizeBeforeUpdate = annoAccademicoRepository.findAll().size();

        // Create the AnnoAccademico
        AnnoAccademicoDTO annoAccademicoDTO = annoAccademicoMapper.toDto(annoAccademico);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAnnoAccademicoMockMvc.perform(put("/api/anno-accademicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(annoAccademicoDTO)))
            .andExpect(status().isCreated());

        // Validate the AnnoAccademico in the database
        List<AnnoAccademico> annoAccademicoList = annoAccademicoRepository.findAll();
        assertThat(annoAccademicoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAnnoAccademico() throws Exception {
        // Initialize the database
        annoAccademicoRepository.saveAndFlush(annoAccademico);
        int databaseSizeBeforeDelete = annoAccademicoRepository.findAll().size();

        // Get the annoAccademico
        restAnnoAccademicoMockMvc.perform(delete("/api/anno-accademicos/{id}", annoAccademico.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AnnoAccademico> annoAccademicoList = annoAccademicoRepository.findAll();
        assertThat(annoAccademicoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnnoAccademico.class);
        AnnoAccademico annoAccademico1 = new AnnoAccademico();
        annoAccademico1.setId(1L);
        AnnoAccademico annoAccademico2 = new AnnoAccademico();
        annoAccademico2.setId(annoAccademico1.getId());
        assertThat(annoAccademico1).isEqualTo(annoAccademico2);
        annoAccademico2.setId(2L);
        assertThat(annoAccademico1).isNotEqualTo(annoAccademico2);
        annoAccademico1.setId(null);
        assertThat(annoAccademico1).isNotEqualTo(annoAccademico2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnnoAccademicoDTO.class);
        AnnoAccademicoDTO annoAccademicoDTO1 = new AnnoAccademicoDTO();
        annoAccademicoDTO1.setId(1L);
        AnnoAccademicoDTO annoAccademicoDTO2 = new AnnoAccademicoDTO();
        assertThat(annoAccademicoDTO1).isNotEqualTo(annoAccademicoDTO2);
        annoAccademicoDTO2.setId(annoAccademicoDTO1.getId());
        assertThat(annoAccademicoDTO1).isEqualTo(annoAccademicoDTO2);
        annoAccademicoDTO2.setId(2L);
        assertThat(annoAccademicoDTO1).isNotEqualTo(annoAccademicoDTO2);
        annoAccademicoDTO1.setId(null);
        assertThat(annoAccademicoDTO1).isNotEqualTo(annoAccademicoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(annoAccademicoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(annoAccademicoMapper.fromId(null)).isNull();
    }
}
