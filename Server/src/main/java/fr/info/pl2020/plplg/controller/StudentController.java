package fr.info.pl2020.plplg.controller;

import fr.info.pl2020.plplg.dto.ErrorResponse;
import fr.info.pl2020.plplg.entity.Student;
import fr.info.pl2020.plplg.entity.TeachingUnit;
import fr.info.pl2020.plplg.exception.ClientRequestException;
import fr.info.pl2020.plplg.security.StudentDetails;
import fr.info.pl2020.plplg.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping(value = "/student", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> getStudent() {
        Student s = this.studentService.getById(getId());
        if (s != null) {
            return new ResponseEntity<>(s, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/student/career", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<TeachingUnit>> getStudentCareer() {
        Student s = this.studentService.getById(getId());
        if (s != null) {
            return new ResponseEntity<>(s.getCareer(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/student/career", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> addTeachingUnitInStudentCareer(@RequestBody @NotNull CreateTeachingUnitRequestBody body) {
        try {
            this.studentService.addTeachingUnitInCareer(getId(), body.teachingUnitId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ClientRequestException cre) {
            return new ResponseEntity<>(cre.getClientMessage(), cre.getStatus());
        }
    }

    @PutMapping(value = "/student/career", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateStudentCareer(@RequestBody @NotNull List<Integer> teachingUnitIdList) {
        try {
            this.studentService.updateCareer(getId(), teachingUnitIdList);
            return new ResponseEntity<>("{}", HttpStatus.OK);
        } catch (ClientRequestException cre) {
            return new ResponseEntity<>(cre.getClientMessage(), cre.getStatus());
        }
    }

    public static class CreateTeachingUnitRequestBody {
        private int teachingUnitId;
    }

    private int getId() {
        return ((StudentDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }
}
