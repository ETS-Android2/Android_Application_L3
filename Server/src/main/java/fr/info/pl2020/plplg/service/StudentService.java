package fr.info.pl2020.plplg.service;

import fr.info.pl2020.plplg.entity.Student;
import fr.info.pl2020.plplg.repository.StudentRepository;
import fr.info.pl2020.plplg.repository.TeachingUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        String encodedPassword = this.passwordEncoder.encode(password);
        Student s = new Student(firstName, lastName, email, encodedPassword);
        return this.studentRepository.save(s);
    }

}
