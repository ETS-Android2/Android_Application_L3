package fr.info.pl2020.plplg.repository;

import fr.info.pl2020.plplg.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Override
    Optional<Category> findById(Integer integer);
}
