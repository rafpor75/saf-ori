package com.saf.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.saf.service.CdlService;
import com.saf.web.rest.errors.BadRequestAlertException;
import com.saf.web.rest.util.HeaderUtil;
import com.saf.web.rest.util.PaginationUtil;
import com.saf.service.dto.CdlDTO;
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
 * REST controller for managing Cdl.
 */
@RestController
@RequestMapping("/api")
public class CdlResource {

    private final Logger log = LoggerFactory.getLogger(CdlResource.class);

    private static final String ENTITY_NAME = "cdl";

    private final CdlService cdlService;

    public CdlResource(CdlService cdlService) {
        this.cdlService = cdlService;
    }

    /**
     * POST  /cdls : Create a new cdl.
     *
     * @param cdlDTO the cdlDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cdlDTO, or with status 400 (Bad Request) if the cdl has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cdls")
    @Timed
    public ResponseEntity<CdlDTO> createCdl(@Valid @RequestBody CdlDTO cdlDTO) throws URISyntaxException {
        log.debug("REST request to save Cdl : {}", cdlDTO);
        if (cdlDTO.getId() != null) {
            throw new BadRequestAlertException("A new cdl cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CdlDTO result = cdlService.save(cdlDTO);
        return ResponseEntity.created(new URI("/api/cdls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cdls : Updates an existing cdl.
     *
     * @param cdlDTO the cdlDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cdlDTO,
     * or with status 400 (Bad Request) if the cdlDTO is not valid,
     * or with status 500 (Internal Server Error) if the cdlDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cdls")
    @Timed
    public ResponseEntity<CdlDTO> updateCdl(@Valid @RequestBody CdlDTO cdlDTO) throws URISyntaxException {
        log.debug("REST request to update Cdl : {}", cdlDTO);
        if (cdlDTO.getId() == null) {
            return createCdl(cdlDTO);
        }
        CdlDTO result = cdlService.save(cdlDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cdlDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cdls : get all the cdls.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cdls in body
     */
    @GetMapping("/cdls")
    @Timed
    public ResponseEntity<List<CdlDTO>> getAllCdls(Pageable pageable) {
        log.debug("REST request to get a page of Cdls");
        Page<CdlDTO> page = cdlService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cdls");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cdls/:id : get the "id" cdl.
     *
     * @param id the id of the cdlDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cdlDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cdls/{id}")
    @Timed
    public ResponseEntity<CdlDTO> getCdl(@PathVariable Long id) {
        log.debug("REST request to get Cdl : {}", id);
        CdlDTO cdlDTO = cdlService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cdlDTO));
    }

    /**
     * DELETE  /cdls/:id : delete the "id" cdl.
     *
     * @param id the id of the cdlDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cdls/{id}")
    @Timed
    public ResponseEntity<Void> deleteCdl(@PathVariable Long id) {
        log.debug("REST request to delete Cdl : {}", id);
        cdlService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
