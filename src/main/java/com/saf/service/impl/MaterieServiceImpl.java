package com.saf.service.impl;

import com.saf.service.MaterieService;
import com.saf.domain.Materie;
import com.saf.repository.MaterieRepository;
import com.saf.service.dto.MaterieDTO;
import com.saf.service.mapper.MaterieMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Materie.
 */
@Service
@Transactional
public class MaterieServiceImpl implements MaterieService {

    private final Logger log = LoggerFactory.getLogger(MaterieServiceImpl.class);

    private final MaterieRepository materieRepository;

    private final MaterieMapper materieMapper;

    public MaterieServiceImpl(MaterieRepository materieRepository, MaterieMapper materieMapper) {
        this.materieRepository = materieRepository;
        this.materieMapper = materieMapper;
    }

    /**
     * Save a materie.
     *
     * @param materieDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MaterieDTO save(MaterieDTO materieDTO) {
        log.debug("Request to save Materie : {}", materieDTO);
        Materie materie = materieMapper.toEntity(materieDTO);
        materie = materieRepository.save(materie);
        return materieMapper.toDto(materie);
    }

    /**
     * Get all the materies.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MaterieDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Materies");
        return materieRepository.findAll(pageable)
            .map(materieMapper::toDto);
    }

    /**
     * Get one materie by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MaterieDTO findOne(Long id) {
        log.debug("Request to get Materie : {}", id);
        Materie materie = materieRepository.findOne(id);
        return materieMapper.toDto(materie);
    }

    /**
     * Delete the materie by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Materie : {}", id);
        materieRepository.delete(id);
    }
}
