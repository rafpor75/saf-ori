package com.saf.service;

import com.saf.service.dto.EsamiDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Esami.
 */
public interface EsamiService {

    /**
     * Save a esami.
     *
     * @param esamiDTO the entity to save
     * @return the persisted entity
     */
    EsamiDTO save(EsamiDTO esamiDTO);

    /**
     * Get all the esamis.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EsamiDTO> findAll(Pageable pageable);

    /**
     * Get the "id" esami.
     *
     * @param id the id of the entity
     * @return the entity
     */
    EsamiDTO findOne(Long id);

    /**
     * Delete the "id" esami.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
