package fr.info.pl2020.plplg.service;

import fr.info.pl2020.plplg.entity.Student;
import fr.info.pl2020.plplg.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student getById(int id) {
        return this.studentRepository.findById(id).orElse(null);
    }

    public Student addStudent(String firstName, String lastName, String email, String password) {
        Student s = new Student(firstName, lastName, email, password);
        return this.studentRepository.save(s);

    }


}
