package com.saf.web.rest;

import com.saf.SafOriApp;

import com.saf.domain.Cdl;
import com.saf.repository.CdlRepository;
import com.saf.service.CdlService;
import com.saf.service.dto.CdlDTO;
import com.saf.service.mapper.CdlMapper;
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
 * Test class for the CdlResource REST controller.
 *
 * @see CdlResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SafOriApp.class)
public class CdlResourceIntTest {

    private static final String DEFAULT_CODICE = "AAAAAAAAAA";
    private static final String UPDATED_CODICE = "BBBBBBBBBB";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ABILITATO = false;
    private static final Boolean UPDATED_ABILITATO = true;

    @Autowired
    private CdlRepository cdlRepository;

    @Autowired
    private CdlMapper cdlMapper;

    @Autowired
    private CdlService cdlService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCdlMockMvc;

    private Cdl cdl;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CdlResource cdlResource = new CdlResource(cdlService);
        this.restCdlMockMvc = MockMvcBuilders.standaloneSetup(cdlResource)
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
    public static Cdl createEntity(EntityManager em) {
        Cdl cdl = new Cdl()
            .codice(DEFAULT_CODICE)
            .nome(DEFAULT_NOME)
            .abilitato(DEFAULT_ABILITATO);
        return cdl;
    }

    @Before
    public void initTest() {
        cdl = createEntity(em);
    }

    @Test
    @Transactional
    public void createCdl() throws Exception {
        int databaseSizeBeforeCreate = cdlRepository.findAll().size();

        // Create the Cdl
        CdlDTO cdlDTO = cdlMapper.toDto(cdl);
        restCdlMockMvc.perform(post("/api/cdls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cdlDTO)))
            .andExpect(status().isCreated());

        // Validate the Cdl in the database
        List<Cdl> cdlList = cdlRepository.findAll();
        assertThat(cdlList).hasSize(databaseSizeBeforeCreate + 1);
        Cdl testCdl = cdlList.get(cdlList.size() - 1);
        assertThat(testCdl.getCodice()).isEqualTo(DEFAULT_CODICE);
        assertThat(testCdl.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testCdl.isAbilitato()).isEqualTo(DEFAULT_ABILITATO);
    }

    @Test
    @Transactional
    public void createCdlWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cdlRepository.findAll().size();

        // Create the Cdl with an existing ID
        cdl.setId(1L);
        CdlDTO cdlDTO = cdlMapper.toDto(cdl);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCdlMockMvc.perform(post("/api/cdls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cdlDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cdl in the database
        List<Cdl> cdlList = cdlRepository.findAll();
        assertThat(cdlList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodiceIsRequired() throws Exception {
        int databaseSizeBeforeTest = cdlRepository.findAll().size();
        // set the field null
        cdl.setCodice(null);

        // Create the Cdl, which fails.
        CdlDTO cdlDTO = cdlMapper.toDto(cdl);

        restCdlMockMvc.perform(post("/api/cdls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cdlDTO)))
            .andExpect(status().isBadRequest());

        List<Cdl> cdlList = cdlRepository.findAll();
        assertThat(cdlList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cdlRepository.findAll().size();
        // set the field null
        cdl.setNome(null);

        // Create the Cdl, which fails.
        CdlDTO cdlDTO = cdlMapper.toDto(cdl);

        restCdlMockMvc.perform(post("/api/cdls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cdlDTO)))
            .andExpect(status().isBadRequest());

        List<Cdl> cdlList = cdlRepository.findAll();
        assertThat(cdlList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCdls() throws Exception {
        // Initialize the database
        cdlRepository.saveAndFlush(cdl);

        // Get all the cdlList
        restCdlMockMvc.perform(get("/api/cdls?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cdl.getId().intValue())))
            .andExpect(jsonPath("$.[*].codice").value(hasItem(DEFAULT_CODICE.toString())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].abilitato").value(hasItem(DEFAULT_ABILITATO.booleanValue())));
    }

    @Test
    @Transactional
    public void getCdl() throws Exception {
        // Initialize the database
        cdlRepository.saveAndFlush(cdl);

        // Get the cdl
        restCdlMockMvc.perform(get("/api/cdls/{id}", cdl.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cdl.getId().intValue()))
            .andExpect(jsonPath("$.codice").value(DEFAULT_CODICE.toString()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.abilitato").value(DEFAULT_ABILITATO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCdl() throws Exception {
        // Get the cdl
        restCdlMockMvc.perform(get("/api/cdls/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCdl() throws Exception {
        // Initialize the database
        cdlRepository.saveAndFlush(cdl);
        int databaseSizeBeforeUpdate = cdlRepository.findAll().size();

        // Update the cdl
        Cdl updatedCdl = cdlRepository.findOne(cdl.getId());
        // Disconnect from session so that the updates on updatedCdl are not directly saved in db
        em.detach(updatedCdl);
        updatedCdl
            .codice(UPDATED_CODICE)
            .nome(UPDATED_NOME)
            .abilitato(UPDATED_ABILITATO);
        CdlDTO cdlDTO = cdlMapper.toDto(updatedCdl);

        restCdlMockMvc.perform(put("/api/cdls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cdlDTO)))
            .andExpect(status().isOk());

        // Validate the Cdl in the database
        List<Cdl> cdlList = cdlRepository.findAll();
        assertThat(cdlList).hasSize(databaseSizeBeforeUpdate);
        Cdl testCdl = cdlList.get(cdlList.size() - 1);
        assertThat(testCdl.getCodice()).isEqualTo(UPDATED_CODICE);
        assertThat(testCdl.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testCdl.isAbilitato()).isEqualTo(UPDATED_ABILITATO);
    }

    @Test
    @Transactional
    public void updateNonExistingCdl() throws Exception {
        int databaseSizeBeforeUpdate = cdlRepository.findAll().size();

        // Create the Cdl
        CdlDTO cdlDTO = cdlMapper.toDto(cdl);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCdlMockMvc.perform(put("/api/cdls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cdlDTO)))
            .andExpect(status().isCreated());

        // Validate the Cdl in the database
        List<Cdl> cdlList = cdlRepository.findAll();
        assertThat(cdlList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCdl() throws Exception {
        // Initialize the database
        cdlRepository.saveAndFlush(cdl);
        int databaseSizeBeforeDelete = cdlRepository.findAll().size();

        // Get the cdl
        restCdlMockMvc.perform(delete("/api/cdls/{id}", cdl.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Cdl> cdlList = cdlRepository.findAll();
        assertThat(cdlList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cdl.class);
        Cdl cdl1 = new Cdl();
        cdl1.setId(1L);
        Cdl cdl2 = new Cdl();
        cdl2.setId(cdl1.getId());
        assertThat(cdl1).isEqualTo(cdl2);
        cdl2.setId(2L);
        assertThat(cdl1).isNotEqualTo(cdl2);
        cdl1.setId(null);
        assertThat(cdl1).isNotEqualTo(cdl2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CdlDTO.class);
        CdlDTO cdlDTO1 = new CdlDTO();
        cdlDTO1.setId(1L);
        CdlDTO cdlDTO2 = new CdlDTO();
        assertThat(cdlDTO1).isNotEqualTo(cdlDTO2);
        cdlDTO2.setId(cdlDTO1.getId());
        assertThat(cdlDTO1).isEqualTo(cdlDTO2);
        cdlDTO2.setId(2L);
        assertThat(cdlDTO1).isNotEqualTo(cdlDTO2);
        cdlDTO1.setId(null);
        assertThat(cdlDTO1).isNotEqualTo(cdlDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(cdlMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(cdlMapper.fromId(null)).isNull();
    }
}
