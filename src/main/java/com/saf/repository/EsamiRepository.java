package com.saf.repository;

import com.saf.domain.Esami;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Esami entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EsamiRepository extends JpaRepository<Esami, Long> {

}
