package com.saf.service.mapper;

import com.saf.domain.*;
import com.saf.service.dto.DocentiDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Docenti and its DTO DocentiDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DocentiMapper extends EntityMapper<DocentiDTO, Docenti> {


    @Mapping(target = "relDocMats", ignore = true)
    Docenti toEntity(DocentiDTO docentiDTO);

    default Docenti fromId(Long id) {
        if (id == null) {
            return null;
        }
        Docenti docenti = new Docenti();
        docenti.setId(id);
        return docenti;
    }
}
