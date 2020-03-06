package fr.info.pl2020.plplg.service;

import fr.info.pl2020.plplg.entity.Category;
import fr.info.pl2020.plplg.entity.Semester;
import fr.info.pl2020.plplg.entity.TeachingUnit;
import fr.info.pl2020.plplg.repository.TeachingUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeachingUnitService {

    @Autowired
    private TeachingUnitRepository teachingUnitRepository;

    public List<TeachingUnit> getAll() {
        return this.teachingUnitRepository.findAll();
    }

    public TeachingUnit getById(int id) {
        return this.teachingUnitRepository.findById(id).orElse(null);
    }

    public List<TeachingUnit> getBySemesterId(int semesterId) {
        return this.teachingUnitRepository.findAllBySemester(semesterId);
    }

    public TeachingUnit addTeachingUnit(String name, String code, String description, Semester semester, Category category) {
        String desc = description == null ? "Indisponible" : description;
        TeachingUnit t = new TeachingUnit(name, code, desc, semester, category);
        return this.teachingUnitRepository.save(t);
    }

}
