package com.saf.service;

import com.saf.service.dto.TutorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Tutor.
 */
public interface TutorService {

    /**
     * Save a tutor.
     *
     * @param tutorDTO the entity to save
     * @return the persisted entity
     */
    TutorDTO save(TutorDTO tutorDTO);

    /**
     * Get all the tutors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TutorDTO> findAll(Pageable pageable);

    /**
     * Get the "id" tutor.
     *
     * @param id the id of the entity
     * @return the entity
     */
    TutorDTO findOne(Long id);

    /**
     * Delete the "id" tutor.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
