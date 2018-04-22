package com.saf.web.rest;

import com.saf.SafOriApp;

import com.saf.domain.Sedi;
import com.saf.repository.SediRepository;
import com.saf.service.SediService;
import com.saf.service.dto.SediDTO;
import com.saf.service.mapper.SediMapper;
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
 * Test class for the SediResource REST controller.
 *
 * @see SediResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SafOriApp.class)
public class SediResourceIntTest {

    private static final String DEFAULT_SEDE = "AAAAAAAAAA";
    private static final String UPDATED_SEDE = "BBBBBBBBBB";

    @Autowired
    private SediRepository sediRepository;

    @Autowired
    private SediMapper sediMapper;

    @Autowired
    private SediService sediService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSediMockMvc;

    private Sedi sedi;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SediResource sediResource = new SediResource(sediService);
        this.restSediMockMvc = MockMvcBuilders.standaloneSetup(sediResource)
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
    public static Sedi createEntity(EntityManager em) {
        Sedi sedi = new Sedi()
            .sede(DEFAULT_SEDE);
        return sedi;
    }

    @Before
    public void initTest() {
        sedi = createEntity(em);
    }

    @Test
    @Transactional
    public void createSedi() throws Exception {
        int databaseSizeBeforeCreate = sediRepository.findAll().size();

        // Create the Sedi
        SediDTO sediDTO = sediMapper.toDto(sedi);
        restSediMockMvc.perform(post("/api/sedis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sediDTO)))
            .andExpect(status().isCreated());

        // Validate the Sedi in the database
        List<Sedi> sediList = sediRepository.findAll();
        assertThat(sediList).hasSize(databaseSizeBeforeCreate + 1);
        Sedi testSedi = sediList.get(sediList.size() - 1);
        assertThat(testSedi.getSede()).isEqualTo(DEFAULT_SEDE);
    }

    @Test
    @Transactional
    public void createSediWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sediRepository.findAll().size();

        // Create the Sedi with an existing ID
        sedi.setId(1L);
        SediDTO sediDTO = sediMapper.toDto(sedi);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSediMockMvc.perform(post("/api/sedis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sediDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sedi in the database
        List<Sedi> sediList = sediRepository.findAll();
        assertThat(sediList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSedeIsRequired() throws Exception {
        int databaseSizeBeforeTest = sediRepository.findAll().size();
        // set the field null
        sedi.setSede(null);

        // Create the Sedi, which fails.
        SediDTO sediDTO = sediMapper.toDto(sedi);

        restSediMockMvc.perform(post("/api/sedis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sediDTO)))
            .andExpect(status().isBadRequest());

        List<Sedi> sediList = sediRepository.findAll();
        assertThat(sediList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSedis() throws Exception {
        // Initialize the database
        sediRepository.saveAndFlush(sedi);

        // Get all the sediList
        restSediMockMvc.perform(get("/api/sedis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sedi.getId().intValue())))
            .andExpect(jsonPath("$.[*].sede").value(hasItem(DEFAULT_SEDE.toString())));
    }

    @Test
    @Transactional
    public void getSedi() throws Exception {
        // Initialize the database
        sediRepository.saveAndFlush(sedi);

        // Get the sedi
        restSediMockMvc.perform(get("/api/sedis/{id}", sedi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sedi.getId().intValue()))
            .andExpect(jsonPath("$.sede").value(DEFAULT_SEDE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSedi() throws Exception {
        // Get the sedi
        restSediMockMvc.perform(get("/api/sedis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSedi() throws Exception {
        // Initialize the database
        sediRepository.saveAndFlush(sedi);
        int databaseSizeBeforeUpdate = sediRepository.findAll().size();

        // Update the sedi
        Sedi updatedSedi = sediRepository.findOne(sedi.getId());
        // Disconnect from session so that the updates on updatedSedi are not directly saved in db
        em.detach(updatedSedi);
        updatedSedi
            .sede(UPDATED_SEDE);
        SediDTO sediDTO = sediMapper.toDto(updatedSedi);

        restSediMockMvc.perform(put("/api/sedis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sediDTO)))
            .andExpect(status().isOk());

        // Validate the Sedi in the database
        List<Sedi> sediList = sediRepository.findAll();
        assertThat(sediList).hasSize(databaseSizeBeforeUpdate);
        Sedi testSedi = sediList.get(sediList.size() - 1);
        assertThat(testSedi.getSede()).isEqualTo(UPDATED_SEDE);
    }

    @Test
    @Transactional
    public void updateNonExistingSedi() throws Exception {
        int databaseSizeBeforeUpdate = sediRepository.findAll().size();

        // Create the Sedi
        SediDTO sediDTO = sediMapper.toDto(sedi);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSediMockMvc.perform(put("/api/sedis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sediDTO)))
            .andExpect(status().isCreated());

        // Validate the Sedi in the database
        List<Sedi> sediList = sediRepository.findAll();
        assertThat(sediList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSedi() throws Exception {
        // Initialize the database
        sediRepository.saveAndFlush(sedi);
        int databaseSizeBeforeDelete = sediRepository.findAll().size();

        // Get the sedi
        restSediMockMvc.perform(delete("/api/sedis/{id}", sedi.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Sedi> sediList = sediRepository.findAll();
        assertThat(sediList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sedi.class);
        Sedi sedi1 = new Sedi();
        sedi1.setId(1L);
        Sedi sedi2 = new Sedi();
        sedi2.setId(sedi1.getId());
        assertThat(sedi1).isEqualTo(sedi2);
        sedi2.setId(2L);
        assertThat(sedi1).isNotEqualTo(sedi2);
        sedi1.setId(null);
        assertThat(sedi1).isNotEqualTo(sedi2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SediDTO.class);
        SediDTO sediDTO1 = new SediDTO();
        sediDTO1.setId(1L);
        SediDTO sediDTO2 = new SediDTO();
        assertThat(sediDTO1).isNotEqualTo(sediDTO2);
        sediDTO2.setId(sediDTO1.getId());
        assertThat(sediDTO1).isEqualTo(sediDTO2);
        sediDTO2.setId(2L);
        assertThat(sediDTO1).isNotEqualTo(sediDTO2);
        sediDTO1.setId(null);
        assertThat(sediDTO1).isNotEqualTo(sediDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sediMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sediMapper.fromId(null)).isNull();
    }
}
