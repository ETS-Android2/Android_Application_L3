package fr.info.pl2020.plplg.service;

import fr.info.pl2020.plplg.entity.Student;
import fr.info.pl2020.plplg.entity.TeachingUnit;
import fr.info.pl2020.plplg.exception.ClientRequestException;
import fr.info.pl2020.plplg.repository.StudentRepository;
import fr.info.pl2020.plplg.repository.TeachingUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Student addStudent(String firstName, String lastName, String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        Student s = new Student(firstName, lastName, email, encodedPassword);
        return this.studentRepository.save(s);
    }

    public void addTeachingUnitInCareer(int studentId, int teachingUnitId) throws ClientRequestException {
        TeachingUnit tu = this.teachingUnitRepository.findById(teachingUnitId).orElseThrow(() -> new ClientRequestException("L'UE demandé n'existe pas.", HttpStatus.NOT_FOUND));

        // Si l'étudiant n'existe pas
        Student s = this.studentRepository.findById(studentId).orElseThrow(() ->
                new ClientRequestException("addTeachingUnitInCareer(" + studentId + ", " + teachingUnitId + ") - Erreur : Aucun étudiant trouvé avec cet identifiant.", "L'étudiant demandé n'existe pas", HttpStatus.NOT_FOUND));

        // Si l'étudiant possède déjà cet UE dans son parcours
        if (s.getCareer().stream().map(TeachingUnit::getId).anyMatch(((Integer) tu.getId())::equals)) {
            throw new ClientRequestException("UE déjà présente dans le parcours de l'étudiant", HttpStatus.CONFLICT);
        }

        s.getCareer().add(tu);
        this.studentRepository.save(s);
    }

    public void updateCareer(int studentId, List<Integer> teachingUnitIdList) throws ClientRequestException {
        List<TeachingUnit> teachingUnits = this.teachingUnitRepository.findAllByIdIn(teachingUnitIdList);

        // Si l'étudiant n'existe pas
        Student s = this.studentRepository.findById(studentId).orElseThrow(() ->
                new ClientRequestException("updateCareer(" + studentId + ", ...) - Erreur : Aucun étudiant trouvé avec cet identifiant.", "L'étudiant demandé n'existe pas", HttpStatus.NOT_FOUND));

        s.setCareer(teachingUnits);
        this.studentRepository.save(s);
    }
}
