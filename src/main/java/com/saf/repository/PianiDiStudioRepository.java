package com.saf.repository;

import com.saf.domain.PianiDiStudio;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the PianiDiStudio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PianiDiStudioRepository extends JpaRepository<PianiDiStudio, Long> {
    @Query("select distinct piani_di_studio from PianiDiStudio piani_di_studio left join fetch piani_di_studio.relPdsMats")
    List<PianiDiStudio> findAllWithEagerRelationships();

    @Query("select piani_di_studio from PianiDiStudio piani_di_studio left join fetch piani_di_studio.relPdsMats where piani_di_studio.id =:id")
    PianiDiStudio findOneWithEagerRelationships(@Param("id") Long id);

}
