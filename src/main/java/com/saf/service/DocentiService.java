package com.saf.service;

import com.saf.service.dto.DocentiDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Docenti.
 */
public interface DocentiService {

    /**
     * Save a docenti.
     *
     * @param docentiDTO the entity to save
     * @return the persisted entity
     */
    DocentiDTO save(DocentiDTO docentiDTO);

    /**
     * Get all the docentis.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DocentiDTO> findAll(Pageable pageable);

    /**
     * Get the "id" docenti.
     *
     * @param id the id of the entity
     * @return the entity
     */
    DocentiDTO findOne(Long id);

    /**
     * Delete the "id" docenti.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
