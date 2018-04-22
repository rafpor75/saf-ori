package com.saf.repository;

import com.saf.domain.Materie;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Materie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MaterieRepository extends JpaRepository<Materie, Long> {

}
