package com.saf.service.mapper;

import com.saf.domain.*;
import com.saf.service.dto.StudentiDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Studenti and its DTO StudentiDTO.
 */
@Mapper(componentModel = "spring", uses = {CdlMapper.class})
public interface StudentiMapper extends EntityMapper<StudentiDTO, Studenti> {

    @Mapping(source = "relStuCdl.id", target = "relStuCdlId")
    StudentiDTO toDto(Studenti studenti);

    @Mapping(source = "relStuCdlId", target = "relStuCdl")
    Studenti toEntity(StudentiDTO studentiDTO);

    default Studenti fromId(Long id) {
        if (id == null) {
            return null;
        }
        Studenti studenti = new Studenti();
        studenti.setId(id);
        return studenti;
    }
}
