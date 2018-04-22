package com.saf.repository;

import com.saf.domain.Sedi;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Sedi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SediRepository extends JpaRepository<Sedi, Long> {

}
