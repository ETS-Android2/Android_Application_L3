package fr.info.pl2020.plplg.repository;

import fr.info.pl2020.plplg.entity.TeachingUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TeachingUnitRepository extends JpaRepository<TeachingUnit, Integer> {
    @Override
    Optional<TeachingUnit> findById(Integer integer);

    @Query("FROM TeachingUnit as ue WHERE ue.semester.id = ?1")
    List<TeachingUnit> findAllBySemester(Integer semesterId);
}
