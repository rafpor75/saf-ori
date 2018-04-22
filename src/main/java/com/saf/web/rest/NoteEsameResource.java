package com.saf.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.saf.service.NoteEsameService;
import com.saf.web.rest.errors.BadRequestAlertException;
import com.saf.web.rest.util.HeaderUtil;
import com.saf.web.rest.util.PaginationUtil;
import com.saf.service.dto.NoteEsameDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing NoteEsame.
 */
@RestController
@RequestMapping("/api")
public class NoteEsameResource {

    private final Logger log = LoggerFactory.getLogger(NoteEsameResource.class);

    private static final String ENTITY_NAME = "noteEsame";

    private final NoteEsameService noteEsameService;

    public NoteEsameResource(NoteEsameService noteEsameService) {
        this.noteEsameService = noteEsameService;
    }

    /**
     * POST  /note-esames : Create a new noteEsame.
     *
     * @param noteEsameDTO the noteEsameDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new noteEsameDTO, or with status 400 (Bad Request) if the noteEsame has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/note-esames")
    @Timed
    public ResponseEntity<NoteEsameDTO> createNoteEsame(@RequestBody NoteEsameDTO noteEsameDTO) throws URISyntaxException {
        log.debug("REST request to save NoteEsame : {}", noteEsameDTO);
        if (noteEsameDTO.getId() != null) {
            throw new BadRequestAlertException("A new noteEsame cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NoteEsameDTO result = noteEsameService.save(noteEsameDTO);
        return ResponseEntity.created(new URI("/api/note-esames/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /note-esames : Updates an existing noteEsame.
     *
     * @param noteEsameDTO the noteEsameDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated noteEsameDTO,
     * or with status 400 (Bad Request) if the noteEsameDTO is not valid,
     * or with status 500 (Internal Server Error) if the noteEsameDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/note-esames")
    @Timed
    public ResponseEntity<NoteEsameDTO> updateNoteEsame(@RequestBody NoteEsameDTO noteEsameDTO) throws URISyntaxException {
        log.debug("REST request to update NoteEsame : {}", noteEsameDTO);
        if (noteEsameDTO.getId() == null) {
            return createNoteEsame(noteEsameDTO);
        }
        NoteEsameDTO result = noteEsameService.save(noteEsameDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, noteEsameDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /note-esames : get all the noteEsames.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of noteEsames in body
     */
    @GetMapping("/note-esames")
    @Timed
    public ResponseEntity<List<NoteEsameDTO>> getAllNoteEsames(Pageable pageable) {
        log.debug("REST request to get a page of NoteEsames");
        Page<NoteEsameDTO> page = noteEsameService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/note-esames");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /note-esames/:id : get the "id" noteEsame.
     *
     * @param id the id of the noteEsameDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the noteEsameDTO, or with status 404 (Not Found)
     */
    @GetMapping("/note-esames/{id}")
    @Timed
    public ResponseEntity<NoteEsameDTO> getNoteEsame(@PathVariable Long id) {
        log.debug("REST request to get NoteEsame : {}", id);
        NoteEsameDTO noteEsameDTO = noteEsameService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(noteEsameDTO));
    }

    /**
     * DELETE  /note-esames/:id : delete the "id" noteEsame.
     *
     * @param id the id of the noteEsameDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/note-esames/{id}")
    @Timed
    public ResponseEntity<Void> deleteNoteEsame(@PathVariable Long id) {
        log.debug("REST request to delete NoteEsame : {}", id);
        noteEsameService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
