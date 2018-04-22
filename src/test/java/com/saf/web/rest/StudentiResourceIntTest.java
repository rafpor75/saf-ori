package com.saf.web.rest;

import com.saf.SafOriApp;

import com.saf.domain.Studenti;
import com.saf.repository.StudentiRepository;
import com.saf.service.StudentiService;
import com.saf.service.dto.StudentiDTO;
import com.saf.service.mapper.StudentiMapper;
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
 * Test class for the StudentiResource REST controller.
 *
 * @see StudentiResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SafOriApp.class)
public class StudentiResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_COGNOME = "AAAAAAAAAA";
    private static final String UPDATED_COGNOME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final String DEFAULT_INDIRIZZO = "AAAAAAAAAA";
    private static final String UPDATED_INDIRIZZO = "BBBBBBBBBB";

    private static final String DEFAULT_CITTA = "AAAAAAAAAA";
    private static final String UPDATED_CITTA = "BBBBBBBBBB";

    private static final String DEFAULT_PROVINCIA = "AAAAAAAAAA";
    private static final String UPDATED_PROVINCIA = "BBBBBBBBBB";

    private static final String DEFAULT_STATO = "AAAAAAAAAA";
    private static final String UPDATED_STATO = "BBBBBBBBBB";

    private static final String DEFAULT_CAP = "AAAAAAAAAA";
    private static final String UPDATED_CAP = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_DI_NASCITA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_DI_NASCITA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LUOGO_DI_NASCITA = "AAAAAAAAAA";
    private static final String UPDATED_LUOGO_DI_NASCITA = "BBBBBBBBBB";

    private static final String DEFAULT_MATRICOLA = "AAAAAAAAAA";
    private static final String UPDATED_MATRICOLA = "BBBBBBBBBB";

    private static final Boolean DEFAULT_TEMPO_PARZIALE = false;
    private static final Boolean UPDATED_TEMPO_PARZIALE = true;

    private static final Boolean DEFAULT_ABILITATO = false;
    private static final Boolean UPDATED_ABILITATO = true;

    private static final LocalDate DEFAULT_DATA_MODIFICA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_MODIFICA = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private StudentiRepository studentiRepository;

    @Autowired
    private StudentiMapper studentiMapper;

    @Autowired
    private StudentiService studentiService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restStudentiMockMvc;

    private Studenti studenti;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StudentiResource studentiResource = new StudentiResource(studentiService);
        this.restStudentiMockMvc = MockMvcBuilders.standaloneSetup(studentiResource)
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
    public static Studenti createEntity(EntityManager em) {
        Studenti studenti = new Studenti()
            .nome(DEFAULT_NOME)
            .cognome(DEFAULT_COGNOME)
            .email(DEFAULT_EMAIL)
            .telefono(DEFAULT_TELEFONO)
            .indirizzo(DEFAULT_INDIRIZZO)
            .citta(DEFAULT_CITTA)
            .provincia(DEFAULT_PROVINCIA)
            .stato(DEFAULT_STATO)
            .cap(DEFAULT_CAP)
            .dataDiNascita(DEFAULT_DATA_DI_NASCITA)
            .luogoDiNascita(DEFAULT_LUOGO_DI_NASCITA)
            .matricola(DEFAULT_MATRICOLA)
            .tempoParziale(DEFAULT_TEMPO_PARZIALE)
            .abilitato(DEFAULT_ABILITATO)
            .dataModifica(DEFAULT_DATA_MODIFICA);
        return studenti;
    }

    @Before
    public void initTest() {
        studenti = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudenti() throws Exception {
        int databaseSizeBeforeCreate = studentiRepository.findAll().size();

        // Create the Studenti
        StudentiDTO studentiDTO = studentiMapper.toDto(studenti);
        restStudentiMockMvc.perform(post("/api/studentis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentiDTO)))
            .andExpect(status().isCreated());

        // Validate the Studenti in the database
        List<Studenti> studentiList = studentiRepository.findAll();
        assertThat(studentiList).hasSize(databaseSizeBeforeCreate + 1);
        Studenti testStudenti = studentiList.get(studentiList.size() - 1);
        assertThat(testStudenti.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testStudenti.getCognome()).isEqualTo(DEFAULT_COGNOME);
        assertThat(testStudenti.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testStudenti.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testStudenti.getIndirizzo()).isEqualTo(DEFAULT_INDIRIZZO);
        assertThat(testStudenti.getCitta()).isEqualTo(DEFAULT_CITTA);
        assertThat(testStudenti.getProvincia()).isEqualTo(DEFAULT_PROVINCIA);
        assertThat(testStudenti.getStato()).isEqualTo(DEFAULT_STATO);
        assertThat(testStudenti.getCap()).isEqualTo(DEFAULT_CAP);
        assertThat(testStudenti.getDataDiNascita()).isEqualTo(DEFAULT_DATA_DI_NASCITA);
        assertThat(testStudenti.getLuogoDiNascita()).isEqualTo(DEFAULT_LUOGO_DI_NASCITA);
        assertThat(testStudenti.getMatricola()).isEqualTo(DEFAULT_MATRICOLA);
        assertThat(testStudenti.isTempoParziale()).isEqualTo(DEFAULT_TEMPO_PARZIALE);
        assertThat(testStudenti.isAbilitato()).isEqualTo(DEFAULT_ABILITATO);
        assertThat(testStudenti.getDataModifica()).isEqualTo(DEFAULT_DATA_MODIFICA);
    }

    @Test
    @Transactional
    public void createStudentiWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studentiRepository.findAll().size();

        // Create the Studenti with an existing ID
        studenti.setId(1L);
        StudentiDTO studentiDTO = studentiMapper.toDto(studenti);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentiMockMvc.perform(post("/api/studentis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentiDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Studenti in the database
        List<Studenti> studentiList = studentiRepository.findAll();
        assertThat(studentiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentiRepository.findAll().size();
        // set the field null
        studenti.setNome(null);

        // Create the Studenti, which fails.
        StudentiDTO studentiDTO = studentiMapper.toDto(studenti);

        restStudentiMockMvc.perform(post("/api/studentis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentiDTO)))
            .andExpect(status().isBadRequest());

        List<Studenti> studentiList = studentiRepository.findAll();
        assertThat(studentiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCognomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentiRepository.findAll().size();
        // set the field null
        studenti.setCognome(null);

        // Create the Studenti, which fails.
        StudentiDTO studentiDTO = studentiMapper.toDto(studenti);

        restStudentiMockMvc.perform(post("/api/studentis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentiDTO)))
            .andExpect(status().isBadRequest());

        List<Studenti> studentiList = studentiRepository.findAll();
        assertThat(studentiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentiRepository.findAll().size();
        // set the field null
        studenti.setEmail(null);

        // Create the Studenti, which fails.
        StudentiDTO studentiDTO = studentiMapper.toDto(studenti);

        restStudentiMockMvc.perform(post("/api/studentis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentiDTO)))
            .andExpect(status().isBadRequest());

        List<Studenti> studentiList = studentiRepository.findAll();
        assertThat(studentiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMatricolaIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentiRepository.findAll().size();
        // set the field null
        studenti.setMatricola(null);

        // Create the Studenti, which fails.
        StudentiDTO studentiDTO = studentiMapper.toDto(studenti);

        restStudentiMockMvc.perform(post("/api/studentis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentiDTO)))
            .andExpect(status().isBadRequest());

        List<Studenti> studentiList = studentiRepository.findAll();
        assertThat(studentiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStudentis() throws Exception {
        // Initialize the database
        studentiRepository.saveAndFlush(studenti);

        // Get all the studentiList
        restStudentiMockMvc.perform(get("/api/studentis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studenti.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].cognome").value(hasItem(DEFAULT_COGNOME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO.toString())))
            .andExpect(jsonPath("$.[*].indirizzo").value(hasItem(DEFAULT_INDIRIZZO.toString())))
            .andExpect(jsonPath("$.[*].citta").value(hasItem(DEFAULT_CITTA.toString())))
            .andExpect(jsonPath("$.[*].provincia").value(hasItem(DEFAULT_PROVINCIA.toString())))
            .andExpect(jsonPath("$.[*].stato").value(hasItem(DEFAULT_STATO.toString())))
            .andExpect(jsonPath("$.[*].cap").value(hasItem(DEFAULT_CAP.toString())))
            .andExpect(jsonPath("$.[*].dataDiNascita").value(hasItem(DEFAULT_DATA_DI_NASCITA.toString())))
            .andExpect(jsonPath("$.[*].luogoDiNascita").value(hasItem(DEFAULT_LUOGO_DI_NASCITA.toString())))
            .andExpect(jsonPath("$.[*].matricola").value(hasItem(DEFAULT_MATRICOLA.toString())))
            .andExpect(jsonPath("$.[*].tempoParziale").value(hasItem(DEFAULT_TEMPO_PARZIALE.booleanValue())))
            .andExpect(jsonPath("$.[*].abilitato").value(hasItem(DEFAULT_ABILITATO.booleanValue())))
            .andExpect(jsonPath("$.[*].dataModifica").value(hasItem(DEFAULT_DATA_MODIFICA.toString())));
    }

    @Test
    @Transactional
    public void getStudenti() throws Exception {
        // Initialize the database
        studentiRepository.saveAndFlush(studenti);

        // Get the studenti
        restStudentiMockMvc.perform(get("/api/studentis/{id}", studenti.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(studenti.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.cognome").value(DEFAULT_COGNOME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO.toString()))
            .andExpect(jsonPath("$.indirizzo").value(DEFAULT_INDIRIZZO.toString()))
            .andExpect(jsonPath("$.citta").value(DEFAULT_CITTA.toString()))
            .andExpect(jsonPath("$.provincia").value(DEFAULT_PROVINCIA.toString()))
            .andExpect(jsonPath("$.stato").value(DEFAULT_STATO.toString()))
            .andExpect(jsonPath("$.cap").value(DEFAULT_CAP.toString()))
            .andExpect(jsonPath("$.dataDiNascita").value(DEFAULT_DATA_DI_NASCITA.toString()))
            .andExpect(jsonPath("$.luogoDiNascita").value(DEFAULT_LUOGO_DI_NASCITA.toString()))
            .andExpect(jsonPath("$.matricola").value(DEFAULT_MATRICOLA.toString()))
            .andExpect(jsonPath("$.tempoParziale").value(DEFAULT_TEMPO_PARZIALE.booleanValue()))
            .andExpect(jsonPath("$.abilitato").value(DEFAULT_ABILITATO.booleanValue()))
            .andExpect(jsonPath("$.dataModifica").value(DEFAULT_DATA_MODIFICA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStudenti() throws Exception {
        // Get the studenti
        restStudentiMockMvc.perform(get("/api/studentis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudenti() throws Exception {
        // Initialize the database
        studentiRepository.saveAndFlush(studenti);
        int databaseSizeBeforeUpdate = studentiRepository.findAll().size();

        // Update the studenti
        Studenti updatedStudenti = studentiRepository.findOne(studenti.getId());
        // Disconnect from session so that the updates on updatedStudenti are not directly saved in db
        em.detach(updatedStudenti);
        updatedStudenti
            .nome(UPDATED_NOME)
            .cognome(UPDATED_COGNOME)
            .email(UPDATED_EMAIL)
            .telefono(UPDATED_TELEFONO)
            .indirizzo(UPDATED_INDIRIZZO)
            .citta(UPDATED_CITTA)
            .provincia(UPDATED_PROVINCIA)
            .stato(UPDATED_STATO)
            .cap(UPDATED_CAP)
            .dataDiNascita(UPDATED_DATA_DI_NASCITA)
            .luogoDiNascita(UPDATED_LUOGO_DI_NASCITA)
            .matricola(UPDATED_MATRICOLA)
            .tempoParziale(UPDATED_TEMPO_PARZIALE)
            .abilitato(UPDATED_ABILITATO)
            .dataModifica(UPDATED_DATA_MODIFICA);
        StudentiDTO studentiDTO = studentiMapper.toDto(updatedStudenti);

        restStudentiMockMvc.perform(put("/api/studentis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentiDTO)))
            .andExpect(status().isOk());

        // Validate the Studenti in the database
        List<Studenti> studentiList = studentiRepository.findAll();
        assertThat(studentiList).hasSize(databaseSizeBeforeUpdate);
        Studenti testStudenti = studentiList.get(studentiList.size() - 1);
        assertThat(testStudenti.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testStudenti.getCognome()).isEqualTo(UPDATED_COGNOME);
        assertThat(testStudenti.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testStudenti.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testStudenti.getIndirizzo()).isEqualTo(UPDATED_INDIRIZZO);
        assertThat(testStudenti.getCitta()).isEqualTo(UPDATED_CITTA);
        assertThat(testStudenti.getProvincia()).isEqualTo(UPDATED_PROVINCIA);
        assertThat(testStudenti.getStato()).isEqualTo(UPDATED_STATO);
        assertThat(testStudenti.getCap()).isEqualTo(UPDATED_CAP);
        assertThat(testStudenti.getDataDiNascita()).isEqualTo(UPDATED_DATA_DI_NASCITA);
        assertThat(testStudenti.getLuogoDiNascita()).isEqualTo(UPDATED_LUOGO_DI_NASCITA);
        assertThat(testStudenti.getMatricola()).isEqualTo(UPDATED_MATRICOLA);
        assertThat(testStudenti.isTempoParziale()).isEqualTo(UPDATED_TEMPO_PARZIALE);
        assertThat(testStudenti.isAbilitato()).isEqualTo(UPDATED_ABILITATO);
        assertThat(testStudenti.getDataModifica()).isEqualTo(UPDATED_DATA_MODIFICA);
    }

    @Test
    @Transactional
    public void updateNonExistingStudenti() throws Exception {
        int databaseSizeBeforeUpdate = studentiRepository.findAll().size();

        // Create the Studenti
        StudentiDTO studentiDTO = studentiMapper.toDto(studenti);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restStudentiMockMvc.perform(put("/api/studentis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentiDTO)))
            .andExpect(status().isCreated());

        // Validate the Studenti in the database
        List<Studenti> studentiList = studentiRepository.findAll();
        assertThat(studentiList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteStudenti() throws Exception {
        // Initialize the database
        studentiRepository.saveAndFlush(studenti);
        int databaseSizeBeforeDelete = studentiRepository.findAll().size();

        // Get the studenti
        restStudentiMockMvc.perform(delete("/api/studentis/{id}", studenti.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Studenti> studentiList = studentiRepository.findAll();
        assertThat(studentiList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Studenti.class);
        Studenti studenti1 = new Studenti();
        studenti1.setId(1L);
        Studenti studenti2 = new Studenti();
        studenti2.setId(studenti1.getId());
        assertThat(studenti1).isEqualTo(studenti2);
        studenti2.setId(2L);
        assertThat(studenti1).isNotEqualTo(studenti2);
        studenti1.setId(null);
        assertThat(studenti1).isNotEqualTo(studenti2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentiDTO.class);
        StudentiDTO studentiDTO1 = new StudentiDTO();
        studentiDTO1.setId(1L);
        StudentiDTO studentiDTO2 = new StudentiDTO();
        assertThat(studentiDTO1).isNotEqualTo(studentiDTO2);
        studentiDTO2.setId(studentiDTO1.getId());
        assertThat(studentiDTO1).isEqualTo(studentiDTO2);
        studentiDTO2.setId(2L);
        assertThat(studentiDTO1).isNotEqualTo(studentiDTO2);
        studentiDTO1.setId(null);
        assertThat(studentiDTO1).isNotEqualTo(studentiDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(studentiMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(studentiMapper.fromId(null)).isNull();
    }
}
