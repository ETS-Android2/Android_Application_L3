package fr.info.pl2020.plplg.controller;

import fr.info.pl2020.plplg.dto.TeachingUnitRequest;
import fr.info.pl2020.plplg.dto.TeachingUnitResponse;
import fr.info.pl2020.plplg.entity.TeachingUnit;
import fr.info.pl2020.plplg.exception.ClientRequestException;
import fr.info.pl2020.plplg.security.StudentDetails;
import fr.info.pl2020.plplg.service.StudentService;
import fr.info.pl2020.plplg.service.TeachingUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TeachingUnitController {

    @Autowired
    private TeachingUnitService teachingUnitService;

    @Autowired
    private StudentService studentService;

    @GetMapping(value = "/teachingUnit", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TeachingUnitResponse>> getAllTeachingUnit(@RequestParam(value = "semester", defaultValue = "0") int semester, @RequestParam(value = "showUserSelection", defaultValue = "false") boolean showUserSelection, @RequestParam(value = "name", defaultValue = "") String name) {
        if (semester == 0) {
            if (name.isEmpty()) {
                return new ResponseEntity<>(TeachingUnitResponse.TeachingUnitListToTeachingUnitResponseList(this.teachingUnitService.getAll()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(TeachingUnitResponse.TeachingUnitListToTeachingUnitResponseList(this.teachingUnitService.getByName(name)), HttpStatus.OK);
            }
        } else {
            try {
                List<TeachingUnit> teachingUnitList = this.teachingUnitService.getBySemesterId(semester);
                if (showUserSelection) {
                    StudentDetails studentDetails = (StudentDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                    List<Integer> teachingUnitsOfStudent = this.studentService.getById(studentDetails.getId()).getCareer().stream().map(TeachingUnit::getId).collect(Collectors.toList());
                    teachingUnitList.forEach(teachingUnit -> {
                        if (teachingUnitsOfStudent.contains(teachingUnit.getId())) {
                            teachingUnit.setSelectedByStudent(true);
                        }
                    });
                }

                return new ResponseEntity<>(TeachingUnitResponse.TeachingUnitListToTeachingUnitResponseList(teachingUnitList), HttpStatus.OK);
            } catch (NumberFormatException nfe) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
    }

    @GetMapping(value = "/teachingUnit/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TeachingUnitResponse> getTeachingUnit(@PathVariable int id) {
        TeachingUnit teachingUnit = this.teachingUnitService.getById(id);
        if (teachingUnit == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new TeachingUnitResponse(teachingUnit), HttpStatus.OK);
    }

    @PostMapping(value = "/teachingUnit", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<?> createTeachingUnit(@RequestBody @NotNull TeachingUnitRequest teachingUnit) {
        try {
            TeachingUnit tu = this.teachingUnitService.addTeachingUnit(teachingUnit.getName(), teachingUnit.getCode(), teachingUnit.getDescription(), teachingUnit.getSemesterId(), teachingUnit.getCategoryId());
            return new ResponseEntity<>(new TeachingUnitResponse(tu), HttpStatus.CREATED);
        } catch (ClientRequestException cre) {
            return new ResponseEntity<>(cre.getClientMessage(), cre.getStatus());
        }
    }
}
