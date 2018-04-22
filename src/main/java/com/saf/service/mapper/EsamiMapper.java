package com.saf.service.mapper;

import com.saf.domain.*;
import com.saf.service.dto.EsamiDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Esami and its DTO EsamiDTO.
 */
@Mapper(componentModel = "spring", uses = {SediMapper.class, MaterieMapper.class})
public interface EsamiMapper extends EntityMapper<EsamiDTO, Esami> {

    @Mapping(source = "relEsamiSedi.id", target = "relEsamiSediId")
    @Mapping(source = "relEsamiSedi.sede", target = "relEsamiSediSede")
    @Mapping(source = "relMatEsami.id", target = "relMatEsamiId")
    EsamiDTO toDto(Esami esami);

    @Mapping(source = "relEsamiSediId", target = "relEsamiSedi")
    @Mapping(source = "relMatEsamiId", target = "relMatEsami")
    Esami toEntity(EsamiDTO esamiDTO);

    default Esami fromId(Long id) {
        if (id == null) {
            return null;
        }
        Esami esami = new Esami();
        esami.setId(id);
        return esami;
    }
}
