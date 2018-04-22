package com.saf.repository;

import com.saf.domain.AnnoAccademico;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AnnoAccademico entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnnoAccademicoRepository extends JpaRepository<AnnoAccademico, Long> {

}
