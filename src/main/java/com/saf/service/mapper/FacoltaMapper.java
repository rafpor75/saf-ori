package com.saf.service.mapper;

import com.saf.domain.*;
import com.saf.service.dto.FacoltaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Facolta and its DTO FacoltaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FacoltaMapper extends EntityMapper<FacoltaDTO, Facolta> {


    @Mapping(target = "relFacCdls", ignore = true)
    Facolta toEntity(FacoltaDTO facoltaDTO);

    default Facolta fromId(Long id) {
        if (id == null) {
            return null;
        }
        Facolta facolta = new Facolta();
        facolta.setId(id);
        return facolta;
    }
}
