package fr.info.pl2020.plplg.controller;

import fr.info.pl2020.plplg.entity.TeachingUnit;
import fr.info.pl2020.plplg.service.StudentService;
import fr.info.pl2020.plplg.service.TeachingUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<TeachingUnit>> getAllTeachingUnit(@RequestParam(value = "semester", defaultValue = "0") int semester, @RequestParam(value = "showUserSelection", defaultValue = "false") boolean showUserSelection, @RequestParam(value = "name", defaultValue = "") String name) {
        if (semester == 0) {
            if (name == "") {
                return new ResponseEntity<>(this.teachingUnitService.getAll(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(this.teachingUnitService.getByName(name), HttpStatus.OK);
            }
        } else {
            try {
                List<TeachingUnit> teachingUnitList = this.teachingUnitService.getBySemesterId(semester);
                if (showUserSelection) {
                    int id = 1; // TODO cette valeur devra être récupérer par le filtre d'authentification
                    List<Integer> teachingUnitsOfStudent = this.studentService.getById(id).getCareer().stream().map(TeachingUnit::getId).collect(Collectors.toList());
                    teachingUnitList.forEach(teachingUnit -> {
                        if (teachingUnitsOfStudent.contains(teachingUnit.getId())) {
                            teachingUnit.setSelectedByStudent(true);
                        }
                    });
                }

                return new ResponseEntity<>(teachingUnitList, HttpStatus.OK);
            } catch (NumberFormatException nfe) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
    }

    @GetMapping(value = "/teachingUnit/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public TeachingUnit getTeachingUnit(@PathVariable int id) { return this.teachingUnitService.getById(id); }

    @PostMapping(value = "/teachingUnit", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<TeachingUnit> createTeachingUnit(@RequestBody @NotNull TeachingUnit teachingUnit) {
        if (teachingUnit.getName() == null || teachingUnit.getCode() == null || teachingUnit.getSemester().getId() == 0 || teachingUnit.getCategory().getId() == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        TeachingUnit tu = this.teachingUnitService.addTeachingUnit(teachingUnit.getName(), teachingUnit.getCode(), teachingUnit.getDescription(), teachingUnit.getSemester(), teachingUnit.getCategory());
        System.out.println(tu);
        return new ResponseEntity<>(tu, HttpStatus.CREATED);
    }
}
