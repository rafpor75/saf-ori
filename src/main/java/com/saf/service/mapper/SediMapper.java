package com.saf.service.mapper;

import com.saf.domain.*;
import com.saf.service.dto.SediDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Sedi and its DTO SediDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SediMapper extends EntityMapper<SediDTO, Sedi> {



    default Sedi fromId(Long id) {
        if (id == null) {
            return null;
        }
        Sedi sedi = new Sedi();
        sedi.setId(id);
        return sedi;
    }
}
