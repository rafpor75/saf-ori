package com.saf.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.saf.service.DocentiService;
import com.saf.web.rest.errors.BadRequestAlertException;
import com.saf.web.rest.util.HeaderUtil;
import com.saf.web.rest.util.PaginationUtil;
import com.saf.service.dto.DocentiDTO;
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
 * REST controller for managing Docenti.
 */
@RestController
@RequestMapping("/api")
public class DocentiResource {

    private final Logger log = LoggerFactory.getLogger(DocentiResource.class);

    private static final String ENTITY_NAME = "docenti";

    private final DocentiService docentiService;

    public DocentiResource(DocentiService docentiService) {
        this.docentiService = docentiService;
    }

    /**
     * POST  /docentis : Create a new docenti.
     *
     * @param docentiDTO the docentiDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new docentiDTO, or with status 400 (Bad Request) if the docenti has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/docentis")
    @Timed
    public ResponseEntity<DocentiDTO> createDocenti(@Valid @RequestBody DocentiDTO docentiDTO) throws URISyntaxException {
        log.debug("REST request to save Docenti : {}", docentiDTO);
        if (docentiDTO.getId() != null) {
            throw new BadRequestAlertException("A new docenti cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocentiDTO result = docentiService.save(docentiDTO);
        return ResponseEntity.created(new URI("/api/docentis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /docentis : Updates an existing docenti.
     *
     * @param docentiDTO the docentiDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated docentiDTO,
     * or with status 400 (Bad Request) if the docentiDTO is not valid,
     * or with status 500 (Internal Server Error) if the docentiDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/docentis")
    @Timed
    public ResponseEntity<DocentiDTO> updateDocenti(@Valid @RequestBody DocentiDTO docentiDTO) throws URISyntaxException {
        log.debug("REST request to update Docenti : {}", docentiDTO);
        if (docentiDTO.getId() == null) {
            return createDocenti(docentiDTO);
        }
        DocentiDTO result = docentiService.save(docentiDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, docentiDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /docentis : get all the docentis.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of docentis in body
     */
    @GetMapping("/docentis")
    @Timed
    public ResponseEntity<List<DocentiDTO>> getAllDocentis(Pageable pageable) {
        log.debug("REST request to get a page of Docentis");
        Page<DocentiDTO> page = docentiService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/docentis");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /docentis/:id : get the "id" docenti.
     *
     * @param id the id of the docentiDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the docentiDTO, or with status 404 (Not Found)
     */
    @GetMapping("/docentis/{id}")
    @Timed
    public ResponseEntity<DocentiDTO> getDocenti(@PathVariable Long id) {
        log.debug("REST request to get Docenti : {}", id);
        DocentiDTO docentiDTO = docentiService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(docentiDTO));
    }

    /**
     * DELETE  /docentis/:id : delete the "id" docenti.
     *
     * @param id the id of the docentiDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/docentis/{id}")
    @Timed
    public ResponseEntity<Void> deleteDocenti(@PathVariable Long id) {
        log.debug("REST request to delete Docenti : {}", id);
        docentiService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
