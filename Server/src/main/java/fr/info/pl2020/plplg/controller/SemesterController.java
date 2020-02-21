package fr.info.pl2020.plplg.controller;

import fr.info.pl2020.plplg.entity.Semester;
import fr.info.pl2020.plplg.service.SemesterService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SemesterController {

    @Autowired
    private SemesterService semesterService;

    @GetMapping("/semester")
    public List<Semester> getAllSemester() {
        return this.semesterService.getAll();
    }

    @GetMapping("/semester/{id}")
    public Semester getSemester(@PathVariable int id) {
        return this.semesterService.getById(id);
    }

    @PostMapping("/semester")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Semester> createSemester() {
        return new ResponseEntity<>(this.semesterService.addSemester(), HttpStatus.CREATED);
    }
}
