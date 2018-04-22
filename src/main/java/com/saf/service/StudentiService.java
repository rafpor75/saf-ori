package com.saf.service;

import com.saf.service.dto.StudentiDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Studenti.
 */
public interface StudentiService {

    /**
     * Save a studenti.
     *
     * @param studentiDTO the entity to save
     * @return the persisted entity
     */
    StudentiDTO save(StudentiDTO studentiDTO);

    /**
     * Get all the studentis.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<StudentiDTO> findAll(Pageable pageable);

    /**
     * Get the "id" studenti.
     *
     * @param id the id of the entity
     * @return the entity
     */
    StudentiDTO findOne(Long id);

    /**
     * Delete the "id" studenti.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
