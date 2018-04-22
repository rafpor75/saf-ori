package com.saf.web.rest;

import com.saf.SafOriApp;

import com.saf.domain.PianiDiStudio;
import com.saf.repository.PianiDiStudioRepository;
import com.saf.service.PianiDiStudioService;
import com.saf.service.dto.PianiDiStudioDTO;
import com.saf.service.mapper.PianiDiStudioMapper;
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
 * Test class for the PianiDiStudioResource REST controller.
 *
 * @see PianiDiStudioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SafOriApp.class)
public class PianiDiStudioResourceIntTest {

    private static final Boolean DEFAULT_ABILITATO = false;
    private static final Boolean UPDATED_ABILITATO = true;

    private static final LocalDate DEFAULT_DATA_MODIFICA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_MODIFICA = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private PianiDiStudioRepository pianiDiStudioRepository;

    @Autowired
    private PianiDiStudioMapper pianiDiStudioMapper;

    @Autowired
    private PianiDiStudioService pianiDiStudioService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPianiDiStudioMockMvc;

    private PianiDiStudio pianiDiStudio;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PianiDiStudioResource pianiDiStudioResource = new PianiDiStudioResource(pianiDiStudioService);
        this.restPianiDiStudioMockMvc = MockMvcBuilders.standaloneSetup(pianiDiStudioResource)
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
    public static PianiDiStudio createEntity(EntityManager em) {
        PianiDiStudio pianiDiStudio = new PianiDiStudio()
            .abilitato(DEFAULT_ABILITATO)
            .dataModifica(DEFAULT_DATA_MODIFICA);
        return pianiDiStudio;
    }

    @Before
    public void initTest() {
        pianiDiStudio = createEntity(em);
    }

    @Test
    @Transactional
    public void createPianiDiStudio() throws Exception {
        int databaseSizeBeforeCreate = pianiDiStudioRepository.findAll().size();

        // Create the PianiDiStudio
        PianiDiStudioDTO pianiDiStudioDTO = pianiDiStudioMapper.toDto(pianiDiStudio);
        restPianiDiStudioMockMvc.perform(post("/api/piani-di-studios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pianiDiStudioDTO)))
            .andExpect(status().isCreated());

        // Validate the PianiDiStudio in the database
        List<PianiDiStudio> pianiDiStudioList = pianiDiStudioRepository.findAll();
        assertThat(pianiDiStudioList).hasSize(databaseSizeBeforeCreate + 1);
        PianiDiStudio testPianiDiStudio = pianiDiStudioList.get(pianiDiStudioList.size() - 1);
        assertThat(testPianiDiStudio.isAbilitato()).isEqualTo(DEFAULT_ABILITATO);
        assertThat(testPianiDiStudio.getDataModifica()).isEqualTo(DEFAULT_DATA_MODIFICA);
    }

    @Test
    @Transactional
    public void createPianiDiStudioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pianiDiStudioRepository.findAll().size();

        // Create the PianiDiStudio with an existing ID
        pianiDiStudio.setId(1L);
        PianiDiStudioDTO pianiDiStudioDTO = pianiDiStudioMapper.toDto(pianiDiStudio);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPianiDiStudioMockMvc.perform(post("/api/piani-di-studios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pianiDiStudioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PianiDiStudio in the database
        List<PianiDiStudio> pianiDiStudioList = pianiDiStudioRepository.findAll();
        assertThat(pianiDiStudioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPianiDiStudios() throws Exception {
        // Initialize the database
        pianiDiStudioRepository.saveAndFlush(pianiDiStudio);

        // Get all the pianiDiStudioList
        restPianiDiStudioMockMvc.perform(get("/api/piani-di-studios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pianiDiStudio.getId().intValue())))
            .andExpect(jsonPath("$.[*].abilitato").value(hasItem(DEFAULT_ABILITATO.booleanValue())))
            .andExpect(jsonPath("$.[*].dataModifica").value(hasItem(DEFAULT_DATA_MODIFICA.toString())));
    }

    @Test
    @Transactional
    public void getPianiDiStudio() throws Exception {
        // Initialize the database
        pianiDiStudioRepository.saveAndFlush(pianiDiStudio);

        // Get the pianiDiStudio
        restPianiDiStudioMockMvc.perform(get("/api/piani-di-studios/{id}", pianiDiStudio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pianiDiStudio.getId().intValue()))
            .andExpect(jsonPath("$.abilitato").value(DEFAULT_ABILITATO.booleanValue()))
            .andExpect(jsonPath("$.dataModifica").value(DEFAULT_DATA_MODIFICA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPianiDiStudio() throws Exception {
        // Get the pianiDiStudio
        restPianiDiStudioMockMvc.perform(get("/api/piani-di-studios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePianiDiStudio() throws Exception {
        // Initialize the database
        pianiDiStudioRepository.saveAndFlush(pianiDiStudio);
        int databaseSizeBeforeUpdate = pianiDiStudioRepository.findAll().size();

        // Update the pianiDiStudio
        PianiDiStudio updatedPianiDiStudio = pianiDiStudioRepository.findOne(pianiDiStudio.getId());
        // Disconnect from session so that the updates on updatedPianiDiStudio are not directly saved in db
        em.detach(updatedPianiDiStudio);
        updatedPianiDiStudio
            .abilitato(UPDATED_ABILITATO)
            .dataModifica(UPDATED_DATA_MODIFICA);
        PianiDiStudioDTO pianiDiStudioDTO = pianiDiStudioMapper.toDto(updatedPianiDiStudio);

        restPianiDiStudioMockMvc.perform(put("/api/piani-di-studios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pianiDiStudioDTO)))
            .andExpect(status().isOk());

        // Validate the PianiDiStudio in the database
        List<PianiDiStudio> pianiDiStudioList = pianiDiStudioRepository.findAll();
        assertThat(pianiDiStudioList).hasSize(databaseSizeBeforeUpdate);
        PianiDiStudio testPianiDiStudio = pianiDiStudioList.get(pianiDiStudioList.size() - 1);
        assertThat(testPianiDiStudio.isAbilitato()).isEqualTo(UPDATED_ABILITATO);
        assertThat(testPianiDiStudio.getDataModifica()).isEqualTo(UPDATED_DATA_MODIFICA);
    }

    @Test
    @Transactional
    public void updateNonExistingPianiDiStudio() throws Exception {
        int databaseSizeBeforeUpdate = pianiDiStudioRepository.findAll().size();

        // Create the PianiDiStudio
        PianiDiStudioDTO pianiDiStudioDTO = pianiDiStudioMapper.toDto(pianiDiStudio);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPianiDiStudioMockMvc.perform(put("/api/piani-di-studios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pianiDiStudioDTO)))
            .andExpect(status().isCreated());

        // Validate the PianiDiStudio in the database
        List<PianiDiStudio> pianiDiStudioList = pianiDiStudioRepository.findAll();
        assertThat(pianiDiStudioList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePianiDiStudio() throws Exception {
        // Initialize the database
        pianiDiStudioRepository.saveAndFlush(pianiDiStudio);
        int databaseSizeBeforeDelete = pianiDiStudioRepository.findAll().size();

        // Get the pianiDiStudio
        restPianiDiStudioMockMvc.perform(delete("/api/piani-di-studios/{id}", pianiDiStudio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PianiDiStudio> pianiDiStudioList = pianiDiStudioRepository.findAll();
        assertThat(pianiDiStudioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PianiDiStudio.class);
        PianiDiStudio pianiDiStudio1 = new PianiDiStudio();
        pianiDiStudio1.setId(1L);
        PianiDiStudio pianiDiStudio2 = new PianiDiStudio();
        pianiDiStudio2.setId(pianiDiStudio1.getId());
        assertThat(pianiDiStudio1).isEqualTo(pianiDiStudio2);
        pianiDiStudio2.setId(2L);
        assertThat(pianiDiStudio1).isNotEqualTo(pianiDiStudio2);
        pianiDiStudio1.setId(null);
        assertThat(pianiDiStudio1).isNotEqualTo(pianiDiStudio2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PianiDiStudioDTO.class);
        PianiDiStudioDTO pianiDiStudioDTO1 = new PianiDiStudioDTO();
        pianiDiStudioDTO1.setId(1L);
        PianiDiStudioDTO pianiDiStudioDTO2 = new PianiDiStudioDTO();
        assertThat(pianiDiStudioDTO1).isNotEqualTo(pianiDiStudioDTO2);
        pianiDiStudioDTO2.setId(pianiDiStudioDTO1.getId());
        assertThat(pianiDiStudioDTO1).isEqualTo(pianiDiStudioDTO2);
        pianiDiStudioDTO2.setId(2L);
        assertThat(pianiDiStudioDTO1).isNotEqualTo(pianiDiStudioDTO2);
        pianiDiStudioDTO1.setId(null);
        assertThat(pianiDiStudioDTO1).isNotEqualTo(pianiDiStudioDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pianiDiStudioMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pianiDiStudioMapper.fromId(null)).isNull();
    }
}
