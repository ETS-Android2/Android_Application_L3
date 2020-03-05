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

    public TeachingUnit addTeachingUnit(String name,String code,Semester semester,Category category ){
        TeachingUnit t = new TeachingUnit(name, code,  semester,category );
        return this.teachingUnitRepository.save(t);
    }

}
