package com.saf.repository;

import com.saf.domain.Studenti;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Studenti entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentiRepository extends JpaRepository<Studenti, Long> {

}
