package fr.info.pl2020.plplg.controller;

import fr.info.pl2020.plplg.dto.CareerRequest;
import fr.info.pl2020.plplg.entity.Student;
import fr.info.pl2020.plplg.entity.TeachingUnit;
import fr.info.pl2020.plplg.exception.ClientRequestException;
import fr.info.pl2020.plplg.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping(value = "/student", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> getStudent() {
        return new ResponseEntity<>(getLoggedStudent(), HttpStatus.OK);
    }

    @GetMapping(value = "/student/career", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<TeachingUnit>> getStudentCareer() {
        List<TeachingUnit> career = getLoggedStudent().getCareer();
        return new ResponseEntity<>(career, career.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);  //TODO tester les 2 cas
    }

    @PostMapping(value = "/student/career", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> addTeachingUnitInStudentCareer(@RequestBody @NotNull @Valid CareerRequest body) {
        try {
            this.studentService.addTeachingUnitInCareer(getLoggedStudent(), body.getTeachingUnitId());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ClientRequestException cre) {
            return new ResponseEntity<>(cre.getClientMessage(), cre.getStatus());
        }
    }

    @PutMapping(value = "/student/career", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateStudentCareer(@RequestBody @NotNull @NotEmpty List<Integer> teachingUnitIdList) {
        try {
            this.studentService.updateCareer(getLoggedStudent(), teachingUnitIdList);
            return new ResponseEntity<>("{}", HttpStatus.OK);
        } catch (ClientRequestException cre) {
            return new ResponseEntity<>(cre.getClientMessage(), cre.getStatus());
        }
    }

    private Student getLoggedStudent() {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return this.studentService.getByLoggedUser(principal);
    }
}
