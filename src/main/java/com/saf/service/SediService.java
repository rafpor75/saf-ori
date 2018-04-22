package com.saf.service;

import com.saf.service.dto.SediDTO;
import java.util.List;

/**
 * Service Interface for managing Sedi.
 */
public interface SediService {

    /**
     * Save a sedi.
     *
     * @param sediDTO the entity to save
     * @return the persisted entity
     */
    SediDTO save(SediDTO sediDTO);

    /**
     * Get all the sedis.
     *
     * @return the list of entities
     */
    List<SediDTO> findAll();

    /**
     * Get the "id" sedi.
     *
     * @param id the id of the entity
     * @return the entity
     */
    SediDTO findOne(Long id);

    /**
     * Delete the "id" sedi.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
