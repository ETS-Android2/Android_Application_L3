package fr.info.pl2020.plplg.service;

import fr.info.pl2020.plplg.dto.CareerRequest;
import fr.info.pl2020.plplg.entity.Career;
import fr.info.pl2020.plplg.entity.Student;
import fr.info.pl2020.plplg.entity.TeachingUnit;
import fr.info.pl2020.plplg.exception.ClientRequestException;
import fr.info.pl2020.plplg.repository.CareerRepository;
import fr.info.pl2020.plplg.repository.StudentRepository;
import fr.info.pl2020.plplg.repository.TeachingUnitRepository;
import fr.info.pl2020.plplg.util.FunctionsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class CareerService {

    @Autowired
    private CareerRepository careerRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeachingUnitRepository teachingUnitRepository;

    private static final int MAX_TU_BY_SEMESTER = 4;
    private static final String DEFAULT_CAREER_NAME = "Mon parcours";

    /**
     * @return le parcours correspondant à l'id envoyé ou null si id ne correspond à aucun parcours
     */
    public Career findById(int careerId) {
        return this.careerRepository.findById(careerId).orElse(null);
    }

    /**
     * @param studentId id de l'étudiant
     * @return le parcours principal de l'étudiant ou null si l'étudiant n'a aucun parcours
     */
    public Career getMainCareer(int studentId) {
        return this.careerRepository.findAllByStudentIdAndMainCareerIsTrue(studentId).orElse(null);
    }

    /**
     * @return le dernier parcours créé par l'étudiant ou null s'il n'a aucun parcours
     */
    public Career getLastCreatedCareer(Student student) {
        return student.getCareers().stream().max(Comparator.comparingInt(Career::getId)).orElse(null);
    }

    /**
     * @return tous les parcours publics
     */
    public List<Career> getAllPublicCareer() {
        return this.careerRepository.findAllBySharedIsTrue();
    }

    /**
     * Créé un nouveau parcours
     *
     * @return le parcours créé
     * @throws ClientRequestException si un parcours existe déjà avec ce nom là
     */
    public Career createCareer(Student student, CareerRequest careerRequest) throws ClientRequestException {
        String name = careerRequest.getName();
        if (FunctionsUtils.isNullOrBlank(name)) {
            name = generateDefaultName(student.getCareers());
        }

        if (this.careerRepository.findByName(name).isPresent()) {
            throw new ClientRequestException("Un parcours existe déjà avec ce nom là", HttpStatus.CONFLICT);
        }

        if (this.careerRepository.countAllByStudentId(student.getId()) == 0) {
            careerRequest.setMainCareer(true);
        } else if (careerRequest.isMainCareer()) {
            Career previousMainCareer = getMainCareer(student.getId());
            if (previousMainCareer != null) {
                previousMainCareer.setMainCareer(false);
                this.careerRepository.save(previousMainCareer);
            }
        }

        Career career = new Career(name, new ArrayList<>(), careerRequest.isPublic(), careerRequest.isMainCareer());
        career.setStudent(student);

        this.careerRepository.save(career);
        return career;
    }

    /**
     * Ajoute une UE dans un parcours
     *
     * @throws ClientRequestException si l'UE n'existe pas ou si l'UE est déjà dans le parcours
     */
    public void addTeachingUnitInCareer(Career career, int teachingUnitId) throws ClientRequestException {
        TeachingUnit tu = this.teachingUnitRepository.findById(teachingUnitId).orElseThrow(() -> new ClientRequestException("L'UE demandé n'existe pas.", HttpStatus.NOT_FOUND));

        // Si l'étudiant possède déjà cet UE dans son parcours
        if (career.getTeachingUnits().stream().map(TeachingUnit::getId).anyMatch(((Integer) tu.getId())::equals)) {
            throw new ClientRequestException("UE déjà présente dans le parcours de l'étudiant", HttpStatus.CONFLICT);
        }

        career.getTeachingUnits().add(tu);
        this.careerRepository.save(career);
    }

    // TODO : REVOIR CETTE METHODE
    public void validateCareer(Student student/*, Career career*/, List<Integer> teachingUnitIdList) throws ClientRequestException {
        List<TeachingUnit> teachingUnits = this.teachingUnitRepository.findAllByIdIn(teachingUnitIdList);
        //teachingUnits.addAll(career.getTeachingUnits());

        List<TeachingUnit> currentValidateTeachingUnit = student.getValidateCareer();
        List<Integer> currentIdListTu = currentValidateTeachingUnit.stream().map(TeachingUnit::getId).collect(Collectors.toList());

        for (int i = teachingUnits.size() - 1; i >= 0; i--) {
            if (currentIdListTu.contains(teachingUnits.get(i).getId())) {
                teachingUnits.remove(i);
            }
        }
        checkPrerequisiteTeachingUnitIsPresent(teachingUnits);
        currentValidateTeachingUnit.addAll(teachingUnits);
        maxiTeachingUnit(currentValidateTeachingUnit);
        //career.setTeachingUnits(currentValidateTeachingUnit);
        student.setValidateCareer(currentValidateTeachingUnit);
        this.studentRepository.save(student);
        //this.careerRepository.save(career);
    }
/*
    public void updateCareer(Career career, List<Integer> teachingUnitIdList, int currentSemesterId) throws ClientRequestException {
        List<TeachingUnit> teachingUnits = this.teachingUnitRepository.findAllByIdIn(teachingUnitIdList);
        List<TeachingUnit> currentTeachingUnits = career.getTeachingUnits();

        currentTeachingUnits.removeIf(tu -> tu.getSemester().getId() == currentSemesterId && !teachingUnitIdList.contains(tu.getId()));

        List<Integer> currentIdList = currentTeachingUnits.stream().map(TeachingUnit::getId).collect(Collectors.toList());

        teachingUnits.removeIf(tu -> currentIdList.contains(tu.getId()));

        checkPrerequisiteTeachingUnits(currentTeachingUnits, teachingUnits);
        currentTeachingUnits.addAll(teachingUnits);
        maxiTeachingUnit(currentTeachingUnits);
        career.setTeachingUnits(currentTeachingUnits);

        this.careerRepository.save(career);
    }*/

    public Career updateCareer(Career career, List<Integer> teachingUnitIdList) throws ClientRequestException {
        List<TeachingUnit> teachingUnits = this.teachingUnitRepository.findAllByIdIn(teachingUnitIdList);

        // Vérification des 4 UE max par semestre
        maxiTeachingUnit(teachingUnits);

        // Vérification des catégories précédentes
        checkCategoryIsPresentInPreviousSemester(teachingUnits);

        // Vérification des UE prérequis
        checkPrerequisiteTeachingUnitIsPresent(teachingUnits);

        career.setTeachingUnits(teachingUnits);
        this.careerRepository.save(career);
        return career;
    }

    /*
        public void checkPrerequisiteTeachingUnits(List<TeachingUnit> currentTeachingUnits, List<TeachingUnit> teachingUnits) throws ClientRequestException {
            Map<Integer, List<Integer>> categoryByYear = new HashMap<>();

            for (TeachingUnit tu : currentTeachingUnits) {
                int year = tu.getSemester().getYear();
                if (categoryByYear.containsKey(year)) {
                    categoryByYear.get(year).add(tu.getCategory().getId());
                } else {
                    List<Integer> categories = new ArrayList<>();
                    categories.add(tu.getCategory().getId());
                    categoryByYear.put(year, categories);
                }
            }

            for (TeachingUnit tu : teachingUnits) {
                int year = tu.getSemester().getYear();
                if (year != 1 && (!categoryByYear.containsKey(year - 1) || (categoryByYear.containsKey(year - 1) && !categoryByYear.get(year - 1).contains(tu.getCategory().getId())))) {
                    throw new ClientRequestException("Vous devez avoir validé une UE de la catégorie " + tu.getCategory().getName() + " à la " + (tu.getSemester().getYear() - 1) + ((tu.getSemester().getYear() - 1) == 1 ? "ère" : "ème") + " année.",
                            HttpStatus.UNPROCESSABLE_ENTITY);
                }
            }
        }
    */

    public void removeCareer(int studentId, int careerId) throws ClientRequestException {
        Student student = this.studentRepository.findById(studentId).orElseThrow(() -> new ClientRequestException("L'étudiant demandé n'existe pas.", HttpStatus.NOT_FOUND));
        student.getCareers().stream().filter(c -> c.getId() == careerId).findFirst().orElseThrow(() -> new ClientRequestException("Le parcours demandé n'existe pas.", HttpStatus.NOT_FOUND));
        student.getCareers().removeIf(c -> c.getId() == careerId);
        this.careerRepository.deleteById(careerId);
        this.studentRepository.save(student);
    }

    public void maxiTeachingUnit(List<TeachingUnit> TeachingUnits) throws ClientRequestException {
        Map<Integer, Integer> TeachingUnitsBySemester = new HashMap<>();
        for (TeachingUnit tu : TeachingUnits) {
            int semesterId = tu.getSemester().getId();
            if (TeachingUnitsBySemester.containsKey(semesterId)) {
                TeachingUnitsBySemester.replace(semesterId, TeachingUnitsBySemester.get(semesterId) + 1);
            } else {
                TeachingUnitsBySemester.put(semesterId, 1);
            }
        }

        for (Integer nbUe : TeachingUnitsBySemester.values()) {
            if (nbUe > MAX_TU_BY_SEMESTER) {
                throw new ClientRequestException("Vous ne pouvez pas sélectionner plus que quatre UE par semestre.",
                        HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }
    }

    public void checkCategoryIsPresentInPreviousSemester(List<TeachingUnit> teachingUnits) throws ClientRequestException {
        Map<Integer, List<Integer>> categoryByYear = new HashMap<>();

        for (TeachingUnit tu : teachingUnits) {
            int year = tu.getSemester().getYear();
            if (categoryByYear.containsKey(year)) {
                categoryByYear.get(year).add(tu.getCategory().getId());
            } else {
                List<Integer> categories = new ArrayList<>();
                categories.add(tu.getCategory().getId());
                categoryByYear.put(year, categories);
            }
        }

        for (TeachingUnit tu : teachingUnits) {
            int year = tu.getSemester().getYear();
            if (year != 1 && (!categoryByYear.containsKey(year - 1) || (categoryByYear.containsKey(year - 1) && !categoryByYear.get(year - 1).contains(tu.getCategory().getId())))) {
                throw new ClientRequestException("Vous devez avoir validé une UE de la catégorie " + tu.getCategory().getName() + " à la " + (tu.getSemester().getYear() - 1) + ((tu.getSemester().getYear() - 1) == 1 ? "ère" : "ème") + " année.",
                        HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }
    }

    public void checkPrerequisiteTeachingUnitIsPresent(List<TeachingUnit> teachingUnits) throws ClientRequestException {
        for (TeachingUnit tu : teachingUnits) {
            for (TeachingUnit prerequisite : tu.getPrerequisite()) {
                if (!teachingUnits.contains(prerequisite)) {
                    throw new ClientRequestException("Vous devez avoir validé l'UE " + prerequisite.getName() + " avant de pouvoir valider " + tu.getName(), HttpStatus.UNPROCESSABLE_ENTITY);
                }
            }
        }
    }

    public String generateDefaultName(List<Career> careerList) {
        if (careerList.isEmpty()) {
            return DEFAULT_CAREER_NAME;
        }

        Pattern patternSimple = Pattern.compile("^" + DEFAULT_CAREER_NAME + "");
        int maxDefault = careerList.stream().map(Career::getName).filter(s -> patternSimple.matcher(s).find()).map(s -> {
            String numberValue = s.substring(DEFAULT_CAREER_NAME.length()).trim();
            return numberValue.equals("") ? 1 : Integer.parseInt(numberValue);
        }).max(Integer::compareTo).orElse(0);

        return DEFAULT_CAREER_NAME + (maxDefault == 0 ? "" : " " + (maxDefault + 1));
    }
}
