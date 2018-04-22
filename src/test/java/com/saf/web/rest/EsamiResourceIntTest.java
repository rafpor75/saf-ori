package com.saf.web.rest;

import com.saf.SafOriApp;

import com.saf.domain.Esami;
import com.saf.repository.EsamiRepository;
import com.saf.service.EsamiService;
import com.saf.service.dto.EsamiDTO;
import com.saf.service.mapper.EsamiMapper;
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
 * Test class for the EsamiResource REST controller.
 *
 * @see EsamiResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SafOriApp.class)
public class EsamiResourceIntTest {

    private static final LocalDate DEFAULT_DATA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private EsamiRepository esamiRepository;

    @Autowired
    private EsamiMapper esamiMapper;

    @Autowired
    private EsamiService esamiService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEsamiMockMvc;

    private Esami esami;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EsamiResource esamiResource = new EsamiResource(esamiService);
        this.restEsamiMockMvc = MockMvcBuilders.standaloneSetup(esamiResource)
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
    public static Esami createEntity(EntityManager em) {
        Esami esami = new Esami()
            .data(DEFAULT_DATA);
        return esami;
    }

    @Before
    public void initTest() {
        esami = createEntity(em);
    }

    @Test
    @Transactional
    public void createEsami() throws Exception {
        int databaseSizeBeforeCreate = esamiRepository.findAll().size();

        // Create the Esami
        EsamiDTO esamiDTO = esamiMapper.toDto(esami);
        restEsamiMockMvc.perform(post("/api/esamis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(esamiDTO)))
            .andExpect(status().isCreated());

        // Validate the Esami in the database
        List<Esami> esamiList = esamiRepository.findAll();
        assertThat(esamiList).hasSize(databaseSizeBeforeCreate + 1);
        Esami testEsami = esamiList.get(esamiList.size() - 1);
        assertThat(testEsami.getData()).isEqualTo(DEFAULT_DATA);
    }

    @Test
    @Transactional
    public void createEsamiWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = esamiRepository.findAll().size();

        // Create the Esami with an existing ID
        esami.setId(1L);
        EsamiDTO esamiDTO = esamiMapper.toDto(esami);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEsamiMockMvc.perform(post("/api/esamis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(esamiDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Esami in the database
        List<Esami> esamiList = esamiRepository.findAll();
        assertThat(esamiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEsamis() throws Exception {
        // Initialize the database
        esamiRepository.saveAndFlush(esami);

        // Get all the esamiList
        restEsamiMockMvc.perform(get("/api/esamis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(esami.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())));
    }

    @Test
    @Transactional
    public void getEsami() throws Exception {
        // Initialize the database
        esamiRepository.saveAndFlush(esami);

        // Get the esami
        restEsamiMockMvc.perform(get("/api/esamis/{id}", esami.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(esami.getId().intValue()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEsami() throws Exception {
        // Get the esami
        restEsamiMockMvc.perform(get("/api/esamis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEsami() throws Exception {
        // Initialize the database
        esamiRepository.saveAndFlush(esami);
        int databaseSizeBeforeUpdate = esamiRepository.findAll().size();

        // Update the esami
        Esami updatedEsami = esamiRepository.findOne(esami.getId());
        // Disconnect from session so that the updates on updatedEsami are not directly saved in db
        em.detach(updatedEsami);
        updatedEsami
            .data(UPDATED_DATA);
        EsamiDTO esamiDTO = esamiMapper.toDto(updatedEsami);

        restEsamiMockMvc.perform(put("/api/esamis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(esamiDTO)))
            .andExpect(status().isOk());

        // Validate the Esami in the database
        List<Esami> esamiList = esamiRepository.findAll();
        assertThat(esamiList).hasSize(databaseSizeBeforeUpdate);
        Esami testEsami = esamiList.get(esamiList.size() - 1);
        assertThat(testEsami.getData()).isEqualTo(UPDATED_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingEsami() throws Exception {
        int databaseSizeBeforeUpdate = esamiRepository.findAll().size();

        // Create the Esami
        EsamiDTO esamiDTO = esamiMapper.toDto(esami);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEsamiMockMvc.perform(put("/api/esamis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(esamiDTO)))
            .andExpect(status().isCreated());

        // Validate the Esami in the database
        List<Esami> esamiList = esamiRepository.findAll();
        assertThat(esamiList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEsami() throws Exception {
        // Initialize the database
        esamiRepository.saveAndFlush(esami);
        int databaseSizeBeforeDelete = esamiRepository.findAll().size();

        // Get the esami
        restEsamiMockMvc.perform(delete("/api/esamis/{id}", esami.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Esami> esamiList = esamiRepository.findAll();
        assertThat(esamiList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Esami.class);
        Esami esami1 = new Esami();
        esami1.setId(1L);
        Esami esami2 = new Esami();
        esami2.setId(esami1.getId());
        assertThat(esami1).isEqualTo(esami2);
        esami2.setId(2L);
        assertThat(esami1).isNotEqualTo(esami2);
        esami1.setId(null);
        assertThat(esami1).isNotEqualTo(esami2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EsamiDTO.class);
        EsamiDTO esamiDTO1 = new EsamiDTO();
        esamiDTO1.setId(1L);
        EsamiDTO esamiDTO2 = new EsamiDTO();
        assertThat(esamiDTO1).isNotEqualTo(esamiDTO2);
        esamiDTO2.setId(esamiDTO1.getId());
        assertThat(esamiDTO1).isEqualTo(esamiDTO2);
        esamiDTO2.setId(2L);
        assertThat(esamiDTO1).isNotEqualTo(esamiDTO2);
        esamiDTO1.setId(null);
        assertThat(esamiDTO1).isNotEqualTo(esamiDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(esamiMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(esamiMapper.fromId(null)).isNull();
    }
}
