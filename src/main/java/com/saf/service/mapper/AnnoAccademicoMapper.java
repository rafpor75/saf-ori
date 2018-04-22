package com.saf.service.mapper;

import com.saf.domain.*;
import com.saf.service.dto.AnnoAccademicoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AnnoAccademico and its DTO AnnoAccademicoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AnnoAccademicoMapper extends EntityMapper<AnnoAccademicoDTO, AnnoAccademico> {



    default AnnoAccademico fromId(Long id) {
        if (id == null) {
            return null;
        }
        AnnoAccademico annoAccademico = new AnnoAccademico();
        annoAccademico.setId(id);
        return annoAccademico;
    }
}
