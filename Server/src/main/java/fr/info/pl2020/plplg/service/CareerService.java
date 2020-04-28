package fr.info.pl2020.plplg.service;

import fr.info.pl2020.plplg.dto.CareerRequest;
import fr.info.pl2020.plplg.entity.Career;
import fr.info.pl2020.plplg.entity.Student;
import fr.info.pl2020.plplg.entity.TeachingUnit;
import fr.info.pl2020.plplg.exception.ClientRequestException;
import fr.info.pl2020.plplg.repository.CareerRepository;
import fr.info.pl2020.plplg.repository.StudentRepository;
import fr.info.pl2020.plplg.repository.TeachingUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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

    public Career findById(int careerId) {
        return this.careerRepository.findById(careerId).orElse(null);
    }

    public Career getMainCareer(int studentId) {
        return this.careerRepository.findAllByStudentIdAndMainCareerIsTrue(studentId).orElse(null); //TODO si on trouve pas le parcours principal d'un étudiant faudrait-il throw ?
    }

    public void addTeachingUnitInCareer(Career career, int teachingUnitId) throws ClientRequestException {
        TeachingUnit tu = this.teachingUnitRepository.findById(teachingUnitId).orElseThrow(() -> new ClientRequestException("L'UE demandé n'existe pas.", HttpStatus.NOT_FOUND));

        // Si l'étudiant possède déjà cet UE dans son parcours
        if (career.getTeachingUnits().stream().map(TeachingUnit::getId).anyMatch(((Integer) tu.getId())::equals)) {
            throw new ClientRequestException("UE déjà présente dans le parcours de l'étudiant", HttpStatus.CONFLICT);
        }

        career.getTeachingUnits().add(tu);
        this.careerRepository.save(career);
    }

    public Career createCareer(Student student, CareerRequest careerRequest){
        Career career= new Career(careerRequest.getName(),new ArrayList<>(),careerRequest.isPublic(),careerRequest.isMainCareer());
        career.setStudent(student);
        this.careerRepository.save(career);
        return career;

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
        checkPrerequisiteTeachingUnits(currentValidateTeachingUnit, teachingUnits);
        currentValidateTeachingUnit.addAll(teachingUnits);
        maxiTeachingUnit(currentValidateTeachingUnit);
        //career.setTeachingUnits(currentValidateTeachingUnit);
        student.setValidateCareer(currentValidateTeachingUnit);
        this.studentRepository.save(student);
        //this.careerRepository.save(career);
    }

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
    }

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

    public void removeCareer(int studentId, int careerId) throws ClientRequestException {
        Student student = this.studentRepository.findById(studentId).orElseThrow(() -> new ClientRequestException("L'étudiant demandé n'existe pas.", HttpStatus.NOT_FOUND));
        student.getCareers().stream().filter(c -> c.getId() == careerId).findFirst().orElseThrow(() -> new ClientRequestException("Le parcours demandé n'existe pas.", HttpStatus.NOT_FOUND));
        student.getCareers().removeIf(c -> c.getId() == careerId);

        this.studentRepository.save(student);
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

    public static void main(String[] args) {
        List<Career> careers = Arrays.asList(new Career("toto", Collections.emptyList(), false, false),
                //new Career("Mon parcours", Collections.emptyList(), false, false),
                new Career("toto Mon parcours 2", Collections.emptyList(), false, false)
                //new Career("toto", Collections.emptyList(), false, false),
                //new Career("Mon parcours 2", Collections.emptyList(), false, false)
        );

        System.out.println(new CareerService().generateDefaultName(careers));
    }
}
