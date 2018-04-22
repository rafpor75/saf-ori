package com.saf.repository;

import com.saf.domain.Facolta;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Facolta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FacoltaRepository extends JpaRepository<Facolta, Long> {

}
