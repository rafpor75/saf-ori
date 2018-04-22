package com.saf.service;

import com.saf.service.dto.CdlDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Cdl.
 */
public interface CdlService {

    /**
     * Save a cdl.
     *
     * @param cdlDTO the entity to save
     * @return the persisted entity
     */
    CdlDTO save(CdlDTO cdlDTO);

    /**
     * Get all the cdls.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CdlDTO> findAll(Pageable pageable);

    /**
     * Get the "id" cdl.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CdlDTO findOne(Long id);

    /**
     * Delete the "id" cdl.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
