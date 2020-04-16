package fr.info.pl2020.plplg.controller;

import fr.info.pl2020.plplg.dto.TeachingUnitRequest;
import fr.info.pl2020.plplg.dto.TeachingUnitResponse;
import fr.info.pl2020.plplg.entity.TeachingUnit;
import fr.info.pl2020.plplg.exception.ClientRequestException;
import fr.info.pl2020.plplg.service.TeachingUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
public class TeachingUnitController {

    @Autowired
    private TeachingUnitService teachingUnitService;

    @GetMapping(value = "/teachingUnit", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TeachingUnitResponse>> getAllTeachingUnit(@RequestParam(value = "semester", defaultValue = "0") int semester, @RequestParam(value = "name", defaultValue = "") String name) {
        List<TeachingUnit> teachingUnitList = this.teachingUnitService.getAll(semester, name);
        return new ResponseEntity<>(TeachingUnitResponse.TeachingUnitListToTeachingUnitResponseList(teachingUnitList), HttpStatus.OK);
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
    public ResponseEntity<?> createTeachingUnit(@RequestBody @NotNull @Valid TeachingUnitRequest teachingUnit) {
        try {
            TeachingUnit tu = this.teachingUnitService.addTeachingUnit(teachingUnit.getName(), teachingUnit.getCode(), teachingUnit.getDescription(), teachingUnit.getSemesterId(), teachingUnit.getCategoryId());
            return new ResponseEntity<>(new TeachingUnitResponse(tu), HttpStatus.CREATED);
        } catch (ClientRequestException cre) {
            return new ResponseEntity<>(cre.getClientMessage(), cre.getStatus());
        }
    }
}
