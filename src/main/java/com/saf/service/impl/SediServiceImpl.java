package com.saf.service.impl;

import com.saf.service.SediService;
import com.saf.domain.Sedi;
import com.saf.repository.SediRepository;
import com.saf.service.dto.SediDTO;
import com.saf.service.mapper.SediMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Sedi.
 */
@Service
@Transactional
public class SediServiceImpl implements SediService {

    private final Logger log = LoggerFactory.getLogger(SediServiceImpl.class);

    private final SediRepository sediRepository;

    private final SediMapper sediMapper;

    public SediServiceImpl(SediRepository sediRepository, SediMapper sediMapper) {
        this.sediRepository = sediRepository;
        this.sediMapper = sediMapper;
    }

    /**
     * Save a sedi.
     *
     * @param sediDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SediDTO save(SediDTO sediDTO) {
        log.debug("Request to save Sedi : {}", sediDTO);
        Sedi sedi = sediMapper.toEntity(sediDTO);
        sedi = sediRepository.save(sedi);
        return sediMapper.toDto(sedi);
    }

    /**
     * Get all the sedis.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SediDTO> findAll() {
        log.debug("Request to get all Sedis");
        return sediRepository.findAll().stream()
            .map(sediMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one sedi by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SediDTO findOne(Long id) {
        log.debug("Request to get Sedi : {}", id);
        Sedi sedi = sediRepository.findOne(id);
        return sediMapper.toDto(sedi);
    }

    /**
     * Delete the sedi by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Sedi : {}", id);
        sediRepository.delete(id);
    }
}
