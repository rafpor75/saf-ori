package com.saf.service.mapper;

import com.saf.domain.*;
import com.saf.service.dto.MaterieDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Materie and its DTO MaterieDTO.
 */
@Mapper(componentModel = "spring", uses = {CdlMapper.class, TutorMapper.class, DocentiMapper.class})
public interface MaterieMapper extends EntityMapper<MaterieDTO, Materie> {

    @Mapping(source = "relMatsCdl.id", target = "relMatsCdlId")
    @Mapping(source = "relMatsCdl.nome", target = "relMatsCdlNome")
    @Mapping(source = "relMatsTut.id", target = "relMatsTutId")
    @Mapping(source = "relMatsTut.cognome", target = "relMatsTutCognome")
    @Mapping(source = "relMatsDoc.id", target = "relMatsDocId")
    @Mapping(source = "relMatsDoc.cognome", target = "relMatsDocCognome")
    MaterieDTO toDto(Materie materie);

    @Mapping(source = "relMatsCdlId", target = "relMatsCdl")
    @Mapping(source = "relMatsTutId", target = "relMatsTut")
    @Mapping(source = "relMatsDocId", target = "relMatsDoc")
    Materie toEntity(MaterieDTO materieDTO);

    default Materie fromId(Long id) {
        if (id == null) {
            return null;
        }
        Materie materie = new Materie();
        materie.setId(id);
        return materie;
    }
}
