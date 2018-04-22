package com.saf.service.mapper;

import com.saf.domain.*;
import com.saf.service.dto.NoteEsameDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity NoteEsame and its DTO NoteEsameDTO.
 */
@Mapper(componentModel = "spring", uses = {StudentiMapper.class, EsamiMapper.class})
public interface NoteEsameMapper extends EntityMapper<NoteEsameDTO, NoteEsame> {

    @Mapping(source = "relNoteStud.id", target = "relNoteStudId")
    @Mapping(source = "relNoteEsami.id", target = "relNoteEsamiId")
    NoteEsameDTO toDto(NoteEsame noteEsame);

    @Mapping(source = "relNoteStudId", target = "relNoteStud")
    @Mapping(source = "relNoteEsamiId", target = "relNoteEsami")
    NoteEsame toEntity(NoteEsameDTO noteEsameDTO);

    default NoteEsame fromId(Long id) {
        if (id == null) {
            return null;
        }
        NoteEsame noteEsame = new NoteEsame();
        noteEsame.setId(id);
        return noteEsame;
    }
}
