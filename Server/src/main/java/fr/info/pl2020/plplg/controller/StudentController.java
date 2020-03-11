package fr.info.pl2020.plplg.controller;

import fr.info.pl2020.plplg.entity.Student;
import fr.info.pl2020.plplg.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping(value = "/student/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Student getStudent(@PathVariable int id) {
        return this.studentService.getById(id);
    }

    @PostMapping(value = "/student", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<Student> createStudent(@RequestBody @NotNull Student student) {
        if (student.getFirstName() == null || student.getLastName() == null || student.getEmail() == null || student.getPassword() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Student s = this.studentService.addStudent(student.getFirstName(), student.getLastName(), student.getEmail(), student.getPassword());
        return new ResponseEntity<>(s, HttpStatus.CREATED);
    }
}
