package com.saf.repository;

import com.saf.domain.NoteEsame;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the NoteEsame entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NoteEsameRepository extends JpaRepository<NoteEsame, Long> {

}
