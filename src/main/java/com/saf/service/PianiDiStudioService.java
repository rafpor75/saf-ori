package com.saf.service;

import com.saf.service.dto.PianiDiStudioDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing PianiDiStudio.
 */
public interface PianiDiStudioService {

    /**
     * Save a pianiDiStudio.
     *
     * @param pianiDiStudioDTO the entity to save
     * @return the persisted entity
     */
    PianiDiStudioDTO save(PianiDiStudioDTO pianiDiStudioDTO);

    /**
     * Get all the pianiDiStudios.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PianiDiStudioDTO> findAll(Pageable pageable);

    /**
     * Get the "id" pianiDiStudio.
     *
     * @param id the id of the entity
     * @return the entity
     */
    PianiDiStudioDTO findOne(Long id);

    /**
     * Delete the "id" pianiDiStudio.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
