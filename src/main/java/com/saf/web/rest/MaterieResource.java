package com.saf.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.saf.service.MaterieService;
import com.saf.web.rest.errors.BadRequestAlertException;
import com.saf.web.rest.util.HeaderUtil;
import com.saf.web.rest.util.PaginationUtil;
import com.saf.service.dto.MaterieDTO;
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
 * REST controller for managing Materie.
 */
@RestController
@RequestMapping("/api")
public class MaterieResource {

    private final Logger log = LoggerFactory.getLogger(MaterieResource.class);

    private static final String ENTITY_NAME = "materie";

    private final MaterieService materieService;

    public MaterieResource(MaterieService materieService) {
        this.materieService = materieService;
    }

    /**
     * POST  /materies : Create a new materie.
     *
     * @param materieDTO the materieDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new materieDTO, or with status 400 (Bad Request) if the materie has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/materies")
    @Timed
    public ResponseEntity<MaterieDTO> createMaterie(@Valid @RequestBody MaterieDTO materieDTO) throws URISyntaxException {
        log.debug("REST request to save Materie : {}", materieDTO);
        if (materieDTO.getId() != null) {
            throw new BadRequestAlertException("A new materie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MaterieDTO result = materieService.save(materieDTO);
        return ResponseEntity.created(new URI("/api/materies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /materies : Updates an existing materie.
     *
     * @param materieDTO the materieDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated materieDTO,
     * or with status 400 (Bad Request) if the materieDTO is not valid,
     * or with status 500 (Internal Server Error) if the materieDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/materies")
    @Timed
    public ResponseEntity<MaterieDTO> updateMaterie(@Valid @RequestBody MaterieDTO materieDTO) throws URISyntaxException {
        log.debug("REST request to update Materie : {}", materieDTO);
        if (materieDTO.getId() == null) {
            return createMaterie(materieDTO);
        }
        MaterieDTO result = materieService.save(materieDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, materieDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /materies : get all the materies.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of materies in body
     */
    @GetMapping("/materies")
    @Timed
    public ResponseEntity<List<MaterieDTO>> getAllMateries(Pageable pageable) {
        log.debug("REST request to get a page of Materies");
        Page<MaterieDTO> page = materieService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/materies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /materies/:id : get the "id" materie.
     *
     * @param id the id of the materieDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the materieDTO, or with status 404 (Not Found)
     */
    @GetMapping("/materies/{id}")
    @Timed
    public ResponseEntity<MaterieDTO> getMaterie(@PathVariable Long id) {
        log.debug("REST request to get Materie : {}", id);
        MaterieDTO materieDTO = materieService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(materieDTO));
    }

    /**
     * DELETE  /materies/:id : delete the "id" materie.
     *
     * @param id the id of the materieDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/materies/{id}")
    @Timed
    public ResponseEntity<Void> deleteMaterie(@PathVariable Long id) {
        log.debug("REST request to delete Materie : {}", id);
        materieService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
