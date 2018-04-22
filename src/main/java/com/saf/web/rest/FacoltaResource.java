package com.saf.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.saf.service.FacoltaService;
import com.saf.web.rest.errors.BadRequestAlertException;
import com.saf.web.rest.util.HeaderUtil;
import com.saf.web.rest.util.PaginationUtil;
import com.saf.service.dto.FacoltaDTO;
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
 * REST controller for managing Facolta.
 */
@RestController
@RequestMapping("/api")
public class FacoltaResource {

    private final Logger log = LoggerFactory.getLogger(FacoltaResource.class);

    private static final String ENTITY_NAME = "facolta";

    private final FacoltaService facoltaService;

    public FacoltaResource(FacoltaService facoltaService) {
        this.facoltaService = facoltaService;
    }

    /**
     * POST  /facoltas : Create a new facolta.
     *
     * @param facoltaDTO the facoltaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new facoltaDTO, or with status 400 (Bad Request) if the facolta has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/facoltas")
    @Timed
    public ResponseEntity<FacoltaDTO> createFacolta(@Valid @RequestBody FacoltaDTO facoltaDTO) throws URISyntaxException {
        log.debug("REST request to save Facolta : {}", facoltaDTO);
        if (facoltaDTO.getId() != null) {
            throw new BadRequestAlertException("A new facolta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FacoltaDTO result = facoltaService.save(facoltaDTO);
        return ResponseEntity.created(new URI("/api/facoltas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /facoltas : Updates an existing facolta.
     *
     * @param facoltaDTO the facoltaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated facoltaDTO,
     * or with status 400 (Bad Request) if the facoltaDTO is not valid,
     * or with status 500 (Internal Server Error) if the facoltaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/facoltas")
    @Timed
    public ResponseEntity<FacoltaDTO> updateFacolta(@Valid @RequestBody FacoltaDTO facoltaDTO) throws URISyntaxException {
        log.debug("REST request to update Facolta : {}", facoltaDTO);
        if (facoltaDTO.getId() == null) {
            return createFacolta(facoltaDTO);
        }
        FacoltaDTO result = facoltaService.save(facoltaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, facoltaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /facoltas : get all the facoltas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of facoltas in body
     */
    @GetMapping("/facoltas")
    @Timed
    public ResponseEntity<List<FacoltaDTO>> getAllFacoltas(Pageable pageable) {
        log.debug("REST request to get a page of Facoltas");
        Page<FacoltaDTO> page = facoltaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/facoltas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /facoltas/:id : get the "id" facolta.
     *
     * @param id the id of the facoltaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the facoltaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/facoltas/{id}")
    @Timed
    public ResponseEntity<FacoltaDTO> getFacolta(@PathVariable Long id) {
        log.debug("REST request to get Facolta : {}", id);
        FacoltaDTO facoltaDTO = facoltaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(facoltaDTO));
    }

    /**
     * DELETE  /facoltas/:id : delete the "id" facolta.
     *
     * @param id the id of the facoltaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/facoltas/{id}")
    @Timed
    public ResponseEntity<Void> deleteFacolta(@PathVariable Long id) {
        log.debug("REST request to delete Facolta : {}", id);
        facoltaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
