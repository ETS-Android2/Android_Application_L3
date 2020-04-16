package fr.info.pl2020.plplg.controller;

import fr.info.pl2020.plplg.dto.CareerRequest;
import fr.info.pl2020.plplg.dto.TeachingUnitResponse;
import fr.info.pl2020.plplg.entity.Career;
import fr.info.pl2020.plplg.entity.Student;
import fr.info.pl2020.plplg.entity.TeachingUnit;
import fr.info.pl2020.plplg.exception.ClientRequestException;
import fr.info.pl2020.plplg.service.AuthenticationService;
import fr.info.pl2020.plplg.service.CareerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CareerController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private CareerService careerService;

    //TODO supprimer
    @GetMapping(value = "/career/main")
    @ResponseBody
    public ResponseEntity<List<TeachingUnitResponse>> getStudentMainCareer(@RequestParam(name = "semester", defaultValue = "0") int semester) {
        Student loggedStudent = this.authenticationService.getLoggedStudent();
        List<TeachingUnit> tuList = this.careerService.getMainCareer(loggedStudent.getId()).getTeachingUnits();
        if (semester != 0) {
            tuList = tuList.stream().filter(teachingUnit -> teachingUnit.getSemester().getId() == semester).collect(Collectors.toList());
        }
        List<TeachingUnitResponse> responseList = TeachingUnitResponse.TeachingUnitListToTeachingUnitResponseList(tuList);
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    //TODO supprimer
    @PutMapping(value = "/career/main")
    @ResponseBody
    public ResponseEntity<?> updateStudentMainCareer(@RequestBody @NotNull @NotEmpty List<Integer> teachingUnitIdList, @RequestParam(name = "semester", defaultValue = "0") int currentSemesterId) {
        try {
            Student loggedStudent = this.authenticationService.getLoggedStudent();
            this.careerService.updateCareer(this.careerService.getMainCareer(loggedStudent.getId()), teachingUnitIdList, currentSemesterId);
            return new ResponseEntity<>("{}", HttpStatus.OK);
        } catch (ClientRequestException cre) {
            return new ResponseEntity<>(cre.getClientMessage(), cre.getStatus());
        }
    }

    @GetMapping(value = "/career/{careerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> getStudentCareer(@PathVariable int careerId) {
        try {
            return new ResponseEntity<>(getCareerByIdAndCheckOwner(careerId), HttpStatus.OK);
        } catch (ClientRequestException e) {
            return new ResponseEntity<>(e.getClientMessage(), e.getStatus());
        }
    }

    @PostMapping(value = "/career/{careerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> addTeachingUnitInStudentCareer(@PathVariable int careerId, @RequestBody @NotNull @Valid CareerRequest body) {
        try {
            this.careerService.addTeachingUnitInCareer(getCareerByIdAndCheckOwner(careerId), body.getTeachingUnitId());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ClientRequestException cre) {
            return new ResponseEntity<>(cre.getClientMessage(), cre.getStatus());
        }
    }

    @PutMapping(value = "/career/{careerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateStudentCareer(@PathVariable int careerId, @RequestBody @NotNull @NotEmpty List<Integer> teachingUnitIdList, @RequestParam(name = "semester", defaultValue = "0") int currentSemesterId) {
        try {
            this.careerService.updateCareer(getCareerByIdAndCheckOwner(careerId), teachingUnitIdList, currentSemesterId);
            return new ResponseEntity<>("{}", HttpStatus.OK);
        } catch (ClientRequestException cre) {
            return new ResponseEntity<>(cre.getClientMessage(), cre.getStatus());
        }
    }

    private Career getCareerByIdAndCheckOwner(int careerId) throws ClientRequestException {
        Career career = this.careerService.findById(careerId);
        if (career == null) {
            throw new ClientRequestException("Le parcours demandé n'existe pas", HttpStatus.NOT_FOUND);
        }

        Student loggedStudent = this.authenticationService.getLoggedStudent();
        if (loggedStudent.getId() != career.getStudent().getId()) {
            throw new ClientRequestException("Le parcours demandé n'existe pas", HttpStatus.NOT_FOUND);
        }

        return career;
    }
}
