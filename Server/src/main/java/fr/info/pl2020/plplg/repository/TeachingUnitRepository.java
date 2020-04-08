package fr.info.pl2020.plplg.repository;

import fr.info.pl2020.plplg.entity.TeachingUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeachingUnitRepository extends JpaRepository<TeachingUnit, Integer> {
    @Override
    Optional<TeachingUnit> findById(Integer integer);

    List<TeachingUnit> findAllByIdIn(List<Integer> ids);

    List<TeachingUnit> findByNameContainingIgnoreCase(String name);

    List<TeachingUnit> findBySemester_IdAndNameContainingIgnoreCase(int semester_id, String name);
}
