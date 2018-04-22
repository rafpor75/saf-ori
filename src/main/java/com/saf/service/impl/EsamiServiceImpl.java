package com.saf.service.impl;

import com.saf.service.EsamiService;
import com.saf.domain.Esami;
import com.saf.repository.EsamiRepository;
import com.saf.service.dto.EsamiDTO;
import com.saf.service.mapper.EsamiMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Esami.
 */
@Service
@Transactional
public class EsamiServiceImpl implements EsamiService {

    private final Logger log = LoggerFactory.getLogger(EsamiServiceImpl.class);

    private final EsamiRepository esamiRepository;

    private final EsamiMapper esamiMapper;

    public EsamiServiceImpl(EsamiRepository esamiRepository, EsamiMapper esamiMapper) {
        this.esamiRepository = esamiRepository;
        this.esamiMapper = esamiMapper;
    }

    /**
     * Save a esami.
     *
     * @param esamiDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EsamiDTO save(EsamiDTO esamiDTO) {
        log.debug("Request to save Esami : {}", esamiDTO);
        Esami esami = esamiMapper.toEntity(esamiDTO);
        esami = esamiRepository.save(esami);
        return esamiMapper.toDto(esami);
    }

    /**
     * Get all the esamis.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EsamiDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Esamis");
        return esamiRepository.findAll(pageable)
            .map(esamiMapper::toDto);
    }

    /**
     * Get one esami by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EsamiDTO findOne(Long id) {
        log.debug("Request to get Esami : {}", id);
        Esami esami = esamiRepository.findOne(id);
        return esamiMapper.toDto(esami);
    }

    /**
     * Delete the esami by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Esami : {}", id);
        esamiRepository.delete(id);
    }
}
