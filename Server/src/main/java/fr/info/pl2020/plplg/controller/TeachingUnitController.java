package fr.info.pl2020.plplg.controller;

import fr.info.pl2020.plplg.entity.Category;
import fr.info.pl2020.plplg.entity.Semester;
import fr.info.pl2020.plplg.entity.TeachingUnit;
import fr.info.pl2020.plplg.service.CategoryService;
import fr.info.pl2020.plplg.service.SemesterService;
import fr.info.pl2020.plplg.service.TeachingUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TeachingUnitController {
    @Autowired
    private SemesterService semesterService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TeachingUnitService teachingUnitService;

    @GetMapping("/teachingUnit")
    public List<TeachingUnit> getAllTeachingUnit() {
        return this.teachingUnitService.getAll();
    }

    @GetMapping("/teachingUnit/{id}")
    public TeachingUnit getTeachingUnit(@PathVariable int id) {
        return this.teachingUnitService.getById(id);
    }

    @PostMapping("/teachingUnit")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<TeachingUnit> createTeachingUnit(@RequestBody String name,@RequestBody String code,@RequestBody Semester semester, @RequestBody Category category ) {
        return new ResponseEntity<>(this.teachingUnitService.addTeachingUnit(name,code,semester,category), HttpStatus.CREATED);
    }

}
