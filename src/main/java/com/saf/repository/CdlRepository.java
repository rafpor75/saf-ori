package com.saf.repository;

import com.saf.domain.Cdl;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Cdl entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CdlRepository extends JpaRepository<Cdl, Long> {

}
