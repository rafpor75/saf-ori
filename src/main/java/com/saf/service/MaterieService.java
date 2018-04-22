package com.saf.service;

import com.saf.service.dto.MaterieDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Materie.
 */
public interface MaterieService {

    /**
     * Save a materie.
     *
     * @param materieDTO the entity to save
     * @return the persisted entity
     */
    MaterieDTO save(MaterieDTO materieDTO);

    /**
     * Get all the materies.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MaterieDTO> findAll(Pageable pageable);

    /**
     * Get the "id" materie.
     *
     * @param id the id of the entity
     * @return the entity
     */
    MaterieDTO findOne(Long id);

    /**
     * Delete the "id" materie.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
