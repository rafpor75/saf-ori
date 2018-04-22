package com.saf.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.saf.service.SediService;
import com.saf.web.rest.errors.BadRequestAlertException;
import com.saf.web.rest.util.HeaderUtil;
import com.saf.service.dto.SediDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Sedi.
 */
@RestController
@RequestMapping("/api")
public class SediResource {

    private final Logger log = LoggerFactory.getLogger(SediResource.class);

    private static final String ENTITY_NAME = "sedi";

    private final SediService sediService;

    public SediResource(SediService sediService) {
        this.sediService = sediService;
    }

    /**
     * POST  /sedis : Create a new sedi.
     *
     * @param sediDTO the sediDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sediDTO, or with status 400 (Bad Request) if the sedi has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sedis")
    @Timed
    public ResponseEntity<SediDTO> createSedi(@Valid @RequestBody SediDTO sediDTO) throws URISyntaxException {
        log.debug("REST request to save Sedi : {}", sediDTO);
        if (sediDTO.getId() != null) {
            throw new BadRequestAlertException("A new sedi cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SediDTO result = sediService.save(sediDTO);
        return ResponseEntity.created(new URI("/api/sedis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sedis : Updates an existing sedi.
     *
     * @param sediDTO the sediDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sediDTO,
     * or with status 400 (Bad Request) if the sediDTO is not valid,
     * or with status 500 (Internal Server Error) if the sediDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sedis")
    @Timed
    public ResponseEntity<SediDTO> updateSedi(@Valid @RequestBody SediDTO sediDTO) throws URISyntaxException {
        log.debug("REST request to update Sedi : {}", sediDTO);
        if (sediDTO.getId() == null) {
            return createSedi(sediDTO);
        }
        SediDTO result = sediService.save(sediDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sediDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sedis : get all the sedis.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sedis in body
     */
    @GetMapping("/sedis")
    @Timed
    public List<SediDTO> getAllSedis() {
        log.debug("REST request to get all Sedis");
        return sediService.findAll();
        }

    /**
     * GET  /sedis/:id : get the "id" sedi.
     *
     * @param id the id of the sediDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sediDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sedis/{id}")
    @Timed
    public ResponseEntity<SediDTO> getSedi(@PathVariable Long id) {
        log.debug("REST request to get Sedi : {}", id);
        SediDTO sediDTO = sediService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sediDTO));
    }

    /**
     * DELETE  /sedis/:id : delete the "id" sedi.
     *
     * @param id the id of the sediDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sedis/{id}")
    @Timed
    public ResponseEntity<Void> deleteSedi(@PathVariable Long id) {
        log.debug("REST request to delete Sedi : {}", id);
        sediService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
