package fr.info.pl2020.plplg.service;

import fr.info.pl2020.plplg.entity.Category;
import fr.info.pl2020.plplg.entity.Semester;
import fr.info.pl2020.plplg.entity.TeachingUnit;
import fr.info.pl2020.plplg.exception.ClientRequestException;
import fr.info.pl2020.plplg.repository.CategoryRepository;
import fr.info.pl2020.plplg.repository.SemesterRepository;
import fr.info.pl2020.plplg.repository.TeachingUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static fr.info.pl2020.plplg.util.FunctionsUtils.isNullOrBlank;

@Service
public class TeachingUnitService {

    @Autowired
    private TeachingUnitRepository teachingUnitRepository;

    @Autowired
    private SemesterRepository semesterRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<TeachingUnit> getAll() {
        return this.teachingUnitRepository.findAll();
    }

    public TeachingUnit getById(int id) {
        return this.teachingUnitRepository.findById(id).orElse(null);
    }

    public List<TeachingUnit> getBySemesterId(int semesterId) {
        return this.teachingUnitRepository.findAllBySemester_Id(semesterId);
    }

    public TeachingUnit addTeachingUnit(String name, String code, String description, int semesterId, int categoryId) throws ClientRequestException {
        Semester s = semesterRepository.findById(semesterId).orElseThrow(() ->
                new ClientRequestException("addTeachingUnit(...) - Erreur : Aucun semestre trouvé avec l'identifiant '" + semesterId + "'", "Le semestre demandé n'existe pas", HttpStatus.NOT_FOUND)
        );

        Category c = categoryRepository.findById(categoryId).orElseThrow(() ->
                new ClientRequestException("addTeachingUnit(...) - Erreur : Aucune categorie trouvée avec l'identifiant '" + categoryId + "'", "La catégorie demandée n'existe pas", HttpStatus.NOT_FOUND)
        );

        String desc = isNullOrBlank(description) ? "Indisponible" : description;
        TeachingUnit t = new TeachingUnit(name, code, desc, s, c);
        return this.teachingUnitRepository.save(t);
    }

    public List<TeachingUnit> getByName(String name) {
        return this.teachingUnitRepository.findByNameContainingIgnoreCase(name);
    }
}
