package fr.info.pl2020.plplg.controller;

import fr.info.pl2020.plplg.entity.Student;
import fr.info.pl2020.plplg.exception.ClientRequestException;
import fr.info.pl2020.plplg.service.AuthenticationService;
import fr.info.pl2020.plplg.service.CareerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
public class StudentController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private CareerService careerService;

    @GetMapping(value = "/student", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> getStudent() {
        return new ResponseEntity<>(this.authenticationService.getLoggedStudent(), HttpStatus.OK);
    }

    @PutMapping(value = "/student/validateCareer", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> validateStudentCareer(@RequestBody @NotNull @NotEmpty List<Integer> teachingUnitIdList) {
        try {
            this.careerService.validateCareer(this.authenticationService.getLoggedStudent(), teachingUnitIdList);
            return new ResponseEntity<>("{}", HttpStatus.OK);
        } catch (ClientRequestException cre) {
            return new ResponseEntity<>(cre.getClientMessage(), cre.getStatus());
        }
    }
}
