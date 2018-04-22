package com.saf.service.impl;

import com.saf.service.StudentiService;
import com.saf.domain.Studenti;
import com.saf.repository.StudentiRepository;
import com.saf.service.dto.StudentiDTO;
import com.saf.service.mapper.StudentiMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Studenti.
 */
@Service
@Transactional
public class StudentiServiceImpl implements StudentiService {

    private final Logger log = LoggerFactory.getLogger(StudentiServiceImpl.class);

    private final StudentiRepository studentiRepository;

    private final StudentiMapper studentiMapper;

    public StudentiServiceImpl(StudentiRepository studentiRepository, StudentiMapper studentiMapper) {
        this.studentiRepository = studentiRepository;
        this.studentiMapper = studentiMapper;
    }

    /**
     * Save a studenti.
     *
     * @param studentiDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public StudentiDTO save(StudentiDTO studentiDTO) {
        log.debug("Request to save Studenti : {}", studentiDTO);
        Studenti studenti = studentiMapper.toEntity(studentiDTO);
        studenti = studentiRepository.save(studenti);
        return studentiMapper.toDto(studenti);
    }

    /**
     * Get all the studentis.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StudentiDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Studentis");
        return studentiRepository.findAll(pageable)
            .map(studentiMapper::toDto);
    }

    /**
     * Get one studenti by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public StudentiDTO findOne(Long id) {
        log.debug("Request to get Studenti : {}", id);
        Studenti studenti = studentiRepository.findOne(id);
        return studentiMapper.toDto(studenti);
    }

    /**
     * Delete the studenti by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Studenti : {}", id);
        studentiRepository.delete(id);
    }
}
