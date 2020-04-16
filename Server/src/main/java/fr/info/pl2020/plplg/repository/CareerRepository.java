package fr.info.pl2020.plplg.repository;

import fr.info.pl2020.plplg.entity.Career;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CareerRepository extends JpaRepository<Career, Integer> {

    @Override
    Optional<Career> findById(Integer integer);

    Optional<Career> findAllByStudentIdAndMainCareerIsTrue(int student_id);
}
