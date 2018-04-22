package com.saf.service.mapper;

import com.saf.domain.*;
import com.saf.service.dto.PianiDiStudioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PianiDiStudio and its DTO PianiDiStudioDTO.
 */
@Mapper(componentModel = "spring", uses = {AnnoAccademicoMapper.class, CdlMapper.class, MaterieMapper.class})
public interface PianiDiStudioMapper extends EntityMapper<PianiDiStudioDTO, PianiDiStudio> {

    @Mapping(source = "relAnnoAccademico.id", target = "relAnnoAccademicoId")
    @Mapping(source = "relPdsCdl.id", target = "relPdsCdlId")
    PianiDiStudioDTO toDto(PianiDiStudio pianiDiStudio);

    @Mapping(source = "relAnnoAccademicoId", target = "relAnnoAccademico")
    @Mapping(source = "relPdsCdlId", target = "relPdsCdl")
    PianiDiStudio toEntity(PianiDiStudioDTO pianiDiStudioDTO);

    default PianiDiStudio fromId(Long id) {
        if (id == null) {
            return null;
        }
        PianiDiStudio pianiDiStudio = new PianiDiStudio();
        pianiDiStudio.setId(id);
        return pianiDiStudio;
    }
}
