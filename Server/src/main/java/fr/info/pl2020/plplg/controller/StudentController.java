package fr.info.pl2020.plplg.controller;

import fr.info.pl2020.plplg.entity.Student;
import fr.info.pl2020.plplg.entity.TeachingUnit;
import fr.info.pl2020.plplg.exception.ClientRequestException;
import fr.info.pl2020.plplg.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping(value = "/student", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> getStudent() {
        int id = 1; // TODO cette valeur devra être récupérer par le filtre d'authentification
        Student s = this.studentService.getById(id);
        if (s != null) {
            return new ResponseEntity<>(s, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/student", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Student> createStudent(@RequestBody @NotNull Student student) {
        if (student.getFirstName() == null || student.getLastName() == null || student.getEmail() == null || student.getPassword() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Student s = this.studentService.addStudent(student.getFirstName(), student.getLastName(), student.getEmail(), student.getPassword());
        return new ResponseEntity<>(s, HttpStatus.CREATED);
    }

    @GetMapping(value = "/student/career", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<TeachingUnit>> getStudentCareer() {
        int id = 1; // TODO cette valeur devra être récupérer par le filtre d'authentification
        Student s = this.studentService.getById(id);
        if (s != null) {
            return new ResponseEntity<>(s.getCareer(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/student/career", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> addTeachingUnitInStudentCareer(@RequestBody @NotNull CreateTeachingUnitRequestBody body) {
        int id = 1; // TODO cette valeur devra être récupérer par le filtre d'authentification
        try {
            this.studentService.addTeachingUnitInCareer(id, body.teachingUnitId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ClientRequestException cre) {
            return new ResponseEntity<>(cre.getClientMessage(), cre.getStatus());
        }
    }

    @PutMapping(value = "/student/career", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateStudentCareer(@RequestBody @NotNull List<Integer> teachingUnitIdList) {
        int id = 1; // TODO cette valeur devra être récupérer par le filtre d'authentification
        try {
            this.studentService.updateCareer(id, teachingUnitIdList);
            return new ResponseEntity<>("{}", HttpStatus.OK);
        } catch (ClientRequestException cre) {
            return new ResponseEntity<>(cre.getClientMessage(), cre.getStatus());
        }
    }

    public static class CreateTeachingUnitRequestBody {
        private int teachingUnitId;
    }
}
