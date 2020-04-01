package fr.info.pl2020.plplg.controller;

import fr.info.pl2020.plplg.entity.Semester;
import fr.info.pl2020.plplg.service.SemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SemesterController {

    @Autowired
    private SemesterService semesterService;

    @GetMapping(value = "/semester", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Semester> getAllSemester() {
        return this.semesterService.getAll();
    }

    @GetMapping(value = "/semester/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Semester getSemester(@PathVariable int id) {
        return this.semesterService.getById(id);
    }

    @PostMapping(value = "/semester", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<Semester> createSemester() {
        return new ResponseEntity<>(this.semesterService.addSemester(), HttpStatus.CREATED);
    }
}
