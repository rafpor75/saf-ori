package com.saf.repository;

import com.saf.domain.Docenti;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Docenti entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocentiRepository extends JpaRepository<Docenti, Long> {

}
