package com.saf.service.impl;

import com.saf.service.CdlService;
import com.saf.domain.Cdl;
import com.saf.repository.CdlRepository;
import com.saf.service.dto.CdlDTO;
import com.saf.service.mapper.CdlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Cdl.
 */
@Service
@Transactional
public class CdlServiceImpl implements CdlService {

    private final Logger log = LoggerFactory.getLogger(CdlServiceImpl.class);

    private final CdlRepository cdlRepository;

    private final CdlMapper cdlMapper;

    public CdlServiceImpl(CdlRepository cdlRepository, CdlMapper cdlMapper) {
        this.cdlRepository = cdlRepository;
        this.cdlMapper = cdlMapper;
    }

    /**
     * Save a cdl.
     *
     * @param cdlDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CdlDTO save(CdlDTO cdlDTO) {
        log.debug("Request to save Cdl : {}", cdlDTO);
        Cdl cdl = cdlMapper.toEntity(cdlDTO);
        cdl = cdlRepository.save(cdl);
        return cdlMapper.toDto(cdl);
    }

    /**
     * Get all the cdls.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CdlDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Cdls");
        return cdlRepository.findAll(pageable)
            .map(cdlMapper::toDto);
    }

    /**
     * Get one cdl by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CdlDTO findOne(Long id) {
        log.debug("Request to get Cdl : {}", id);
        Cdl cdl = cdlRepository.findOne(id);
        return cdlMapper.toDto(cdl);
    }

    /**
     * Delete the cdl by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cdl : {}", id);
        cdlRepository.delete(id);
    }
}
