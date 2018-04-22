package com.saf.service.mapper;

import com.saf.domain.*;
import com.saf.service.dto.CdlDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Cdl and its DTO CdlDTO.
 */
@Mapper(componentModel = "spring", uses = {FacoltaMapper.class})
public interface CdlMapper extends EntityMapper<CdlDTO, Cdl> {

    @Mapping(source = "relCdlsFac.id", target = "relCdlsFacId")
    @Mapping(source = "relCdlsFac.nome", target = "relCdlsFacNome")
    CdlDTO toDto(Cdl cdl);

    @Mapping(target = "relCdlMats", ignore = true)
    @Mapping(source = "relCdlsFacId", target = "relCdlsFac")
    Cdl toEntity(CdlDTO cdlDTO);

    default Cdl fromId(Long id) {
        if (id == null) {
            return null;
        }
        Cdl cdl = new Cdl();
        cdl.setId(id);
        return cdl;
    }
}
