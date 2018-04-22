package com.saf.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.saf.service.StudentiService;
import com.saf.web.rest.errors.BadRequestAlertException;
import com.saf.web.rest.util.HeaderUtil;
import com.saf.web.rest.util.PaginationUtil;
import com.saf.service.dto.StudentiDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Studenti.
 */
@RestController
@RequestMapping("/api")
public class StudentiResource {

    private final Logger log = LoggerFactory.getLogger(StudentiResource.class);

    private static final String ENTITY_NAME = "studenti";

    private final StudentiService studentiService;

    public StudentiResource(StudentiService studentiService) {
        this.studentiService = studentiService;
    }

    /**
     * POST  /studentis : Create a new studenti.
     *
     * @param studentiDTO the studentiDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new studentiDTO, or with status 400 (Bad Request) if the studenti has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/studentis")
    @Timed
    public ResponseEntity<StudentiDTO> createStudenti(@Valid @RequestBody StudentiDTO studentiDTO) throws URISyntaxException {
        log.debug("REST request to save Studenti : {}", studentiDTO);
        if (studentiDTO.getId() != null) {
            throw new BadRequestAlertException("A new studenti cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StudentiDTO result = studentiService.save(studentiDTO);
        return ResponseEntity.created(new URI("/api/studentis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /studentis : Updates an existing studenti.
     *
     * @param studentiDTO the studentiDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated studentiDTO,
     * or with status 400 (Bad Request) if the studentiDTO is not valid,
     * or with status 500 (Internal Server Error) if the studentiDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/studentis")
    @Timed
    public ResponseEntity<StudentiDTO> updateStudenti(@Valid @RequestBody StudentiDTO studentiDTO) throws URISyntaxException {
        log.debug("REST request to update Studenti : {}", studentiDTO);
        if (studentiDTO.getId() == null) {
            return createStudenti(studentiDTO);
        }
        StudentiDTO result = studentiService.save(studentiDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, studentiDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /studentis : get all the studentis.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of studentis in body
     */
    @GetMapping("/studentis")
    @Timed
    public ResponseEntity<List<StudentiDTO>> getAllStudentis(Pageable pageable) {
        log.debug("REST request to get a page of Studentis");
        Page<StudentiDTO> page = studentiService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/studentis");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /studentis/:id : get the "id" studenti.
     *
     * @param id the id of the studentiDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the studentiDTO, or with status 404 (Not Found)
     */
    @GetMapping("/studentis/{id}")
    @Timed
    public ResponseEntity<StudentiDTO> getStudenti(@PathVariable Long id) {
        log.debug("REST request to get Studenti : {}", id);
        StudentiDTO studentiDTO = studentiService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(studentiDTO));
    }

    /**
     * DELETE  /studentis/:id : delete the "id" studenti.
     *
     * @param id the id of the studentiDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/studentis/{id}")
    @Timed
    public ResponseEntity<Void> deleteStudenti(@PathVariable Long id) {
        log.debug("REST request to delete Studenti : {}", id);
        studentiService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
