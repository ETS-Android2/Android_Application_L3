package fr.info.pl2020.plplg.repository;

import fr.info.pl2020.plplg.entity.Semester;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SemesterRepository extends JpaRepository<Semester, Integer> {

    @Override
    Optional<Semester> findById(Integer integer);
}
