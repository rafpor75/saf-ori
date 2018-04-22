package com.saf.service;

import com.saf.service.dto.AnnoAccademicoDTO;
import java.util.List;

/**
 * Service Interface for managing AnnoAccademico.
 */
public interface AnnoAccademicoService {

    /**
     * Save a annoAccademico.
     *
     * @param annoAccademicoDTO the entity to save
     * @return the persisted entity
     */
    AnnoAccademicoDTO save(AnnoAccademicoDTO annoAccademicoDTO);

    /**
     * Get all the annoAccademicos.
     *
     * @return the list of entities
     */
    List<AnnoAccademicoDTO> findAll();

    /**
     * Get the "id" annoAccademico.
     *
     * @param id the id of the entity
     * @return the entity
     */
    AnnoAccademicoDTO findOne(Long id);

    /**
     * Delete the "id" annoAccademico.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
