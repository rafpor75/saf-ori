package com.saf.service.impl;

import com.saf.service.DocentiService;
import com.saf.domain.Docenti;
import com.saf.repository.DocentiRepository;
import com.saf.service.dto.DocentiDTO;
import com.saf.service.mapper.DocentiMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Docenti.
 */
@Service
@Transactional
public class DocentiServiceImpl implements DocentiService {

    private final Logger log = LoggerFactory.getLogger(DocentiServiceImpl.class);

    private final DocentiRepository docentiRepository;

    private final DocentiMapper docentiMapper;

    public DocentiServiceImpl(DocentiRepository docentiRepository, DocentiMapper docentiMapper) {
        this.docentiRepository = docentiRepository;
        this.docentiMapper = docentiMapper;
    }

    /**
     * Save a docenti.
     *
     * @param docentiDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DocentiDTO save(DocentiDTO docentiDTO) {
        log.debug("Request to save Docenti : {}", docentiDTO);
        Docenti docenti = docentiMapper.toEntity(docentiDTO);
        docenti = docentiRepository.save(docenti);
        return docentiMapper.toDto(docenti);
    }

    /**
     * Get all the docentis.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DocentiDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Docentis");
        return docentiRepository.findAll(pageable)
            .map(docentiMapper::toDto);
    }

    /**
     * Get one docenti by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DocentiDTO findOne(Long id) {
        log.debug("Request to get Docenti : {}", id);
        Docenti docenti = docentiRepository.findOne(id);
        return docentiMapper.toDto(docenti);
    }

    /**
     * Delete the docenti by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Docenti : {}", id);
        docentiRepository.delete(id);
    }
}
