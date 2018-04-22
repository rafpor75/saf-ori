package com.saf.service.mapper;

import com.saf.domain.*;
import com.saf.service.dto.TutorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Tutor and its DTO TutorDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TutorMapper extends EntityMapper<TutorDTO, Tutor> {


    @Mapping(target = "relTutMats", ignore = true)
    Tutor toEntity(TutorDTO tutorDTO);

    default Tutor fromId(Long id) {
        if (id == null) {
            return null;
        }
        Tutor tutor = new Tutor();
        tutor.setId(id);
        return tutor;
    }
}
