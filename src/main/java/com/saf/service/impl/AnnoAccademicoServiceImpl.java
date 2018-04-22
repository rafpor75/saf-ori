package com.saf.service.impl;

import com.saf.service.AnnoAccademicoService;
import com.saf.domain.AnnoAccademico;
import com.saf.repository.AnnoAccademicoRepository;
import com.saf.service.dto.AnnoAccademicoDTO;
import com.saf.service.mapper.AnnoAccademicoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing AnnoAccademico.
 */
@Service
@Transactional
public class AnnoAccademicoServiceImpl implements AnnoAccademicoService {

    private final Logger log = LoggerFactory.getLogger(AnnoAccademicoServiceImpl.class);

    private final AnnoAccademicoRepository annoAccademicoRepository;

    private final AnnoAccademicoMapper annoAccademicoMapper;

    public AnnoAccademicoServiceImpl(AnnoAccademicoRepository annoAccademicoRepository, AnnoAccademicoMapper annoAccademicoMapper) {
        this.annoAccademicoRepository = annoAccademicoRepository;
        this.annoAccademicoMapper = annoAccademicoMapper;
    }

    /**
     * Save a annoAccademico.
     *
     * @param annoAccademicoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AnnoAccademicoDTO save(AnnoAccademicoDTO annoAccademicoDTO) {
        log.debug("Request to save AnnoAccademico : {}", annoAccademicoDTO);
        AnnoAccademico annoAccademico = annoAccademicoMapper.toEntity(annoAccademicoDTO);
        annoAccademico = annoAccademicoRepository.save(annoAccademico);
        return annoAccademicoMapper.toDto(annoAccademico);
    }

    /**
     * Get all the annoAccademicos.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AnnoAccademicoDTO> findAll() {
        log.debug("Request to get all AnnoAccademicos");
        return annoAccademicoRepository.findAll().stream()
            .map(annoAccademicoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one annoAccademico by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AnnoAccademicoDTO findOne(Long id) {
        log.debug("Request to get AnnoAccademico : {}", id);
        AnnoAccademico annoAccademico = annoAccademicoRepository.findOne(id);
        return annoAccademicoMapper.toDto(annoAccademico);
    }

    /**
     * Delete the annoAccademico by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AnnoAccademico : {}", id);
        annoAccademicoRepository.delete(id);
    }
}
