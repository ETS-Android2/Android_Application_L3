package fr.info.pl2020.plplg.repository;


import fr.info.pl2020.plplg.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    @Override
    Optional<Student> findById(Integer integer);

}
