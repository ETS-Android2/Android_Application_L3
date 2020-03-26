package fr.info.pl2020.plplg.service;

import fr.info.pl2020.plplg.entity.Student;
import fr.info.pl2020.plplg.entity.TeachingUnit;
import fr.info.pl2020.plplg.exception.ClientRequestException;
import fr.info.pl2020.plplg.repository.StudentRepository;
import fr.info.pl2020.plplg.repository.TeachingUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonList;

@Service
public class StudentService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeachingUnitRepository teachingUnitRepository;

    public Student getById(int id) {
        return this.studentRepository.findById(id).orElse(null);
    }

    public Student getByEmail(String email) {
        return this.studentRepository.findByEmail(email).orElse(null);
    }

    public Student getByLoggedUser(User user) {
        Student student = getByEmail(user.getUsername());
        if (student == null) {
            throw new IllegalArgumentException("L'utilisateur authentifié n'est pas dans la base de données");
        }

        return student;
    }

    public Student addStudent(String firstName, String lastName, String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        Student s = new Student(firstName, lastName, email, encodedPassword);
        return this.studentRepository.save(s);
    }

    public void addTeachingUnitInCareer(Student student, int teachingUnitId) throws ClientRequestException {
        TeachingUnit tu = this.teachingUnitRepository.findById(teachingUnitId).orElseThrow(() -> new ClientRequestException("L'UE demandé n'existe pas.", HttpStatus.NOT_FOUND));

        // Si l'étudiant possède déjà cet UE dans son parcours
        if (student.getCareer().stream().map(TeachingUnit::getId).anyMatch(((Integer) tu.getId())::equals)) {
            throw new ClientRequestException("UE déjà présente dans le parcours de l'étudiant", HttpStatus.CONFLICT);
        }

        student.getCareer().add(tu);
        this.studentRepository.save(student);
    }

    public void updateCareer(Student student, List<Integer> teachingUnitIdList) throws ClientRequestException {
        List<TeachingUnit> teachingUnits = this.teachingUnitRepository.findAllByIdIn(teachingUnitIdList);

        List<TeachingUnit> currentTeachingUnits = student.getCareer();
        checkPrerequisiteTeachingUnits(currentTeachingUnits, teachingUnits);

        currentTeachingUnits.addAll(teachingUnits);
        maxiTeachingUnit(currentTeachingUnits);
        student.setCareer(currentTeachingUnits);
        this.studentRepository.save(student);
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
            System.out.println("Year :" + year + ", contains year : " + categoryByYear.containsKey(year - 1));
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
            if (nbUe > 4) {
                throw new ClientRequestException("Vous ne pouvez pas sélectionner plus que quatre UE par semestre.",
                        HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }


    }

}
