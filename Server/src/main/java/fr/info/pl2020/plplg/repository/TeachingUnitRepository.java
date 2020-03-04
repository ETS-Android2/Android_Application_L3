package fr.info.pl2020.plplg.repository;



import fr.info.pl2020.plplg.entity.TeachingUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeachingUnitRepository extends JpaRepository<TeachingUnit, Integer> {
    @Override
    Optional<TeachingUnit> findById(Integer integer);
}
