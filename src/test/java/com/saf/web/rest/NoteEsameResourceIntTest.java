package com.saf.web.rest;

import com.saf.SafOriApp;

import com.saf.domain.NoteEsame;
import com.saf.repository.NoteEsameRepository;
import com.saf.service.NoteEsameService;
import com.saf.service.dto.NoteEsameDTO;
import com.saf.service.mapper.NoteEsameMapper;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.saf.web.rest.TestUtil.sameInstant;
import static com.saf.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the NoteEsameResource REST controller.
 *
 * @see NoteEsameResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SafOriApp.class)
public class NoteEsameResourceIntTest {

    private static final String DEFAULT_APPUNTI = "AAAAAAAAAA";
    private static final String UPDATED_APPUNTI = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_DISPENSA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_DISPENSA = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATA_CORSI = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_CORSI = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATA_ABI = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_ABI = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATA_RIEPILOGO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_RIEPILOGO = LocalDate.now(ZoneId.systemDefault());

    private static final ZonedDateTime DEFAULT_ORA_ESAME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ORA_ESAME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_COSTO_ESAME = 1;
    private static final Integer UPDATED_COSTO_ESAME = 2;

    private static final String DEFAULT_FASCE = "AAAAAAAAAA";
    private static final String UPDATED_FASCE = "BBBBBBBBBB";

    private static final String DEFAULT_PIVA = "AAAAAAAAAA";
    private static final String UPDATED_PIVA = "BBBBBBBBBB";

    private static final Boolean DEFAULT_FATTURA = false;
    private static final Boolean UPDATED_FATTURA = true;

    private static final String DEFAULT_NOTE_FATTURA = "AAAAAAAAAA";
    private static final String UPDATED_NOTE_FATTURA = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EMAIL_INVIATA = false;
    private static final Boolean UPDATED_EMAIL_INVIATA = true;

    @Autowired
    private NoteEsameRepository noteEsameRepository;

    @Autowired
    private NoteEsameMapper noteEsameMapper;

    @Autowired
    private NoteEsameService noteEsameService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNoteEsameMockMvc;

    private NoteEsame noteEsame;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NoteEsameResource noteEsameResource = new NoteEsameResource(noteEsameService);
        this.restNoteEsameMockMvc = MockMvcBuilders.standaloneSetup(noteEsameResource)
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
    public static NoteEsame createEntity(EntityManager em) {
        NoteEsame noteEsame = new NoteEsame()
            .appunti(DEFAULT_APPUNTI)
            .dataDispensa(DEFAULT_DATA_DISPENSA)
            .dataCorsi(DEFAULT_DATA_CORSI)
            .dataABI(DEFAULT_DATA_ABI)
            .dataRiepilogo(DEFAULT_DATA_RIEPILOGO)
            .oraEsame(DEFAULT_ORA_ESAME)
            .costoEsame(DEFAULT_COSTO_ESAME)
            .fasce(DEFAULT_FASCE)
            .piva(DEFAULT_PIVA)
            .fattura(DEFAULT_FATTURA)
            .noteFattura(DEFAULT_NOTE_FATTURA)
            .emailInviata(DEFAULT_EMAIL_INVIATA);
        return noteEsame;
    }

    @Before
    public void initTest() {
        noteEsame = createEntity(em);
    }

    @Test
    @Transactional
    public void createNoteEsame() throws Exception {
        int databaseSizeBeforeCreate = noteEsameRepository.findAll().size();

        // Create the NoteEsame
        NoteEsameDTO noteEsameDTO = noteEsameMapper.toDto(noteEsame);
        restNoteEsameMockMvc.perform(post("/api/note-esames")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(noteEsameDTO)))
            .andExpect(status().isCreated());

        // Validate the NoteEsame in the database
        List<NoteEsame> noteEsameList = noteEsameRepository.findAll();
        assertThat(noteEsameList).hasSize(databaseSizeBeforeCreate + 1);
        NoteEsame testNoteEsame = noteEsameList.get(noteEsameList.size() - 1);
        assertThat(testNoteEsame.getAppunti()).isEqualTo(DEFAULT_APPUNTI);
        assertThat(testNoteEsame.getDataDispensa()).isEqualTo(DEFAULT_DATA_DISPENSA);
        assertThat(testNoteEsame.getDataCorsi()).isEqualTo(DEFAULT_DATA_CORSI);
        assertThat(testNoteEsame.getDataABI()).isEqualTo(DEFAULT_DATA_ABI);
        assertThat(testNoteEsame.getDataRiepilogo()).isEqualTo(DEFAULT_DATA_RIEPILOGO);
        assertThat(testNoteEsame.getOraEsame()).isEqualTo(DEFAULT_ORA_ESAME);
        assertThat(testNoteEsame.getCostoEsame()).isEqualTo(DEFAULT_COSTO_ESAME);
        assertThat(testNoteEsame.getFasce()).isEqualTo(DEFAULT_FASCE);
        assertThat(testNoteEsame.getPiva()).isEqualTo(DEFAULT_PIVA);
        assertThat(testNoteEsame.isFattura()).isEqualTo(DEFAULT_FATTURA);
        assertThat(testNoteEsame.getNoteFattura()).isEqualTo(DEFAULT_NOTE_FATTURA);
        assertThat(testNoteEsame.isEmailInviata()).isEqualTo(DEFAULT_EMAIL_INVIATA);
    }

    @Test
    @Transactional
    public void createNoteEsameWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = noteEsameRepository.findAll().size();

        // Create the NoteEsame with an existing ID
        noteEsame.setId(1L);
        NoteEsameDTO noteEsameDTO = noteEsameMapper.toDto(noteEsame);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNoteEsameMockMvc.perform(post("/api/note-esames")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(noteEsameDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NoteEsame in the database
        List<NoteEsame> noteEsameList = noteEsameRepository.findAll();
        assertThat(noteEsameList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllNoteEsames() throws Exception {
        // Initialize the database
        noteEsameRepository.saveAndFlush(noteEsame);

        // Get all the noteEsameList
        restNoteEsameMockMvc.perform(get("/api/note-esames?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(noteEsame.getId().intValue())))
            .andExpect(jsonPath("$.[*].appunti").value(hasItem(DEFAULT_APPUNTI.toString())))
            .andExpect(jsonPath("$.[*].dataDispensa").value(hasItem(DEFAULT_DATA_DISPENSA.toString())))
            .andExpect(jsonPath("$.[*].dataCorsi").value(hasItem(DEFAULT_DATA_CORSI.toString())))
            .andExpect(jsonPath("$.[*].dataABI").value(hasItem(DEFAULT_DATA_ABI.toString())))
            .andExpect(jsonPath("$.[*].dataRiepilogo").value(hasItem(DEFAULT_DATA_RIEPILOGO.toString())))
            .andExpect(jsonPath("$.[*].oraEsame").value(hasItem(sameInstant(DEFAULT_ORA_ESAME))))
            .andExpect(jsonPath("$.[*].costoEsame").value(hasItem(DEFAULT_COSTO_ESAME)))
            .andExpect(jsonPath("$.[*].fasce").value(hasItem(DEFAULT_FASCE.toString())))
            .andExpect(jsonPath("$.[*].piva").value(hasItem(DEFAULT_PIVA.toString())))
            .andExpect(jsonPath("$.[*].fattura").value(hasItem(DEFAULT_FATTURA.booleanValue())))
            .andExpect(jsonPath("$.[*].noteFattura").value(hasItem(DEFAULT_NOTE_FATTURA.toString())))
            .andExpect(jsonPath("$.[*].emailInviata").value(hasItem(DEFAULT_EMAIL_INVIATA.booleanValue())));
    }

    @Test
    @Transactional
    public void getNoteEsame() throws Exception {
        // Initialize the database
        noteEsameRepository.saveAndFlush(noteEsame);

        // Get the noteEsame
        restNoteEsameMockMvc.perform(get("/api/note-esames/{id}", noteEsame.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(noteEsame.getId().intValue()))
            .andExpect(jsonPath("$.appunti").value(DEFAULT_APPUNTI.toString()))
            .andExpect(jsonPath("$.dataDispensa").value(DEFAULT_DATA_DISPENSA.toString()))
            .andExpect(jsonPath("$.dataCorsi").value(DEFAULT_DATA_CORSI.toString()))
            .andExpect(jsonPath("$.dataABI").value(DEFAULT_DATA_ABI.toString()))
            .andExpect(jsonPath("$.dataRiepilogo").value(DEFAULT_DATA_RIEPILOGO.toString()))
            .andExpect(jsonPath("$.oraEsame").value(sameInstant(DEFAULT_ORA_ESAME)))
            .andExpect(jsonPath("$.costoEsame").value(DEFAULT_COSTO_ESAME))
            .andExpect(jsonPath("$.fasce").value(DEFAULT_FASCE.toString()))
            .andExpect(jsonPath("$.piva").value(DEFAULT_PIVA.toString()))
            .andExpect(jsonPath("$.fattura").value(DEFAULT_FATTURA.booleanValue()))
            .andExpect(jsonPath("$.noteFattura").value(DEFAULT_NOTE_FATTURA.toString()))
            .andExpect(jsonPath("$.emailInviata").value(DEFAULT_EMAIL_INVIATA.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingNoteEsame() throws Exception {
        // Get the noteEsame
        restNoteEsameMockMvc.perform(get("/api/note-esames/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNoteEsame() throws Exception {
        // Initialize the database
        noteEsameRepository.saveAndFlush(noteEsame);
        int databaseSizeBeforeUpdate = noteEsameRepository.findAll().size();

        // Update the noteEsame
        NoteEsame updatedNoteEsame = noteEsameRepository.findOne(noteEsame.getId());
        // Disconnect from session so that the updates on updatedNoteEsame are not directly saved in db
        em.detach(updatedNoteEsame);
        updatedNoteEsame
            .appunti(UPDATED_APPUNTI)
            .dataDispensa(UPDATED_DATA_DISPENSA)
            .dataCorsi(UPDATED_DATA_CORSI)
            .dataABI(UPDATED_DATA_ABI)
            .dataRiepilogo(UPDATED_DATA_RIEPILOGO)
            .oraEsame(UPDATED_ORA_ESAME)
            .costoEsame(UPDATED_COSTO_ESAME)
            .fasce(UPDATED_FASCE)
            .piva(UPDATED_PIVA)
            .fattura(UPDATED_FATTURA)
            .noteFattura(UPDATED_NOTE_FATTURA)
            .emailInviata(UPDATED_EMAIL_INVIATA);
        NoteEsameDTO noteEsameDTO = noteEsameMapper.toDto(updatedNoteEsame);

        restNoteEsameMockMvc.perform(put("/api/note-esames")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(noteEsameDTO)))
            .andExpect(status().isOk());

        // Validate the NoteEsame in the database
        List<NoteEsame> noteEsameList = noteEsameRepository.findAll();
        assertThat(noteEsameList).hasSize(databaseSizeBeforeUpdate);
        NoteEsame testNoteEsame = noteEsameList.get(noteEsameList.size() - 1);
        assertThat(testNoteEsame.getAppunti()).isEqualTo(UPDATED_APPUNTI);
        assertThat(testNoteEsame.getDataDispensa()).isEqualTo(UPDATED_DATA_DISPENSA);
        assertThat(testNoteEsame.getDataCorsi()).isEqualTo(UPDATED_DATA_CORSI);
        assertThat(testNoteEsame.getDataABI()).isEqualTo(UPDATED_DATA_ABI);
        assertThat(testNoteEsame.getDataRiepilogo()).isEqualTo(UPDATED_DATA_RIEPILOGO);
        assertThat(testNoteEsame.getOraEsame()).isEqualTo(UPDATED_ORA_ESAME);
        assertThat(testNoteEsame.getCostoEsame()).isEqualTo(UPDATED_COSTO_ESAME);
        assertThat(testNoteEsame.getFasce()).isEqualTo(UPDATED_FASCE);
        assertThat(testNoteEsame.getPiva()).isEqualTo(UPDATED_PIVA);
        assertThat(testNoteEsame.isFattura()).isEqualTo(UPDATED_FATTURA);
        assertThat(testNoteEsame.getNoteFattura()).isEqualTo(UPDATED_NOTE_FATTURA);
        assertThat(testNoteEsame.isEmailInviata()).isEqualTo(UPDATED_EMAIL_INVIATA);
    }

    @Test
    @Transactional
    public void updateNonExistingNoteEsame() throws Exception {
        int databaseSizeBeforeUpdate = noteEsameRepository.findAll().size();

        // Create the NoteEsame
        NoteEsameDTO noteEsameDTO = noteEsameMapper.toDto(noteEsame);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNoteEsameMockMvc.perform(put("/api/note-esames")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(noteEsameDTO)))
            .andExpect(status().isCreated());

        // Validate the NoteEsame in the database
        List<NoteEsame> noteEsameList = noteEsameRepository.findAll();
        assertThat(noteEsameList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteNoteEsame() throws Exception {
        // Initialize the database
        noteEsameRepository.saveAndFlush(noteEsame);
        int databaseSizeBeforeDelete = noteEsameRepository.findAll().size();

        // Get the noteEsame
        restNoteEsameMockMvc.perform(delete("/api/note-esames/{id}", noteEsame.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NoteEsame> noteEsameList = noteEsameRepository.findAll();
        assertThat(noteEsameList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NoteEsame.class);
        NoteEsame noteEsame1 = new NoteEsame();
        noteEsame1.setId(1L);
        NoteEsame noteEsame2 = new NoteEsame();
        noteEsame2.setId(noteEsame1.getId());
        assertThat(noteEsame1).isEqualTo(noteEsame2);
        noteEsame2.setId(2L);
        assertThat(noteEsame1).isNotEqualTo(noteEsame2);
        noteEsame1.setId(null);
        assertThat(noteEsame1).isNotEqualTo(noteEsame2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NoteEsameDTO.class);
        NoteEsameDTO noteEsameDTO1 = new NoteEsameDTO();
        noteEsameDTO1.setId(1L);
        NoteEsameDTO noteEsameDTO2 = new NoteEsameDTO();
        assertThat(noteEsameDTO1).isNotEqualTo(noteEsameDTO2);
        noteEsameDTO2.setId(noteEsameDTO1.getId());
        assertThat(noteEsameDTO1).isEqualTo(noteEsameDTO2);
        noteEsameDTO2.setId(2L);
        assertThat(noteEsameDTO1).isNotEqualTo(noteEsameDTO2);
        noteEsameDTO1.setId(null);
        assertThat(noteEsameDTO1).isNotEqualTo(noteEsameDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(noteEsameMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(noteEsameMapper.fromId(null)).isNull();
    }
}
