package fr.info.pl2020.plplg.repository;

import fr.info.pl2020.plplg.entity.Career;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CareerRepository extends JpaRepository<Career, Integer> {

    @Override
    Optional<Career> findById(Integer integer);

    Optional<Career> findAllByStudentIdAndName(int student_id, String name);

    Optional<Career> findAllByStudentIdAndMainCareerIsTrue(int student_id);

    List<Career> findAllBySharedIsTrue();

    int countAllByStudentId(int student_id);
}
