package fr.info.pl2020.plplg.controller;

import fr.info.pl2020.plplg.entity.Category;
import fr.info.pl2020.plplg.entity.Semester;
import fr.info.pl2020.plplg.service.CategoryService;
import fr.info.pl2020.plplg.service.SemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@RestController
public class SemesterController {

    @Autowired
    private SemesterService semesterService;

    @Autowired
    private CategoryService categoryService;



    @GetMapping("/semester")
    public List<Semester> getAllSemester() {
        return this.semesterService.getAll();
    }

    @GetMapping("/semesterAll")
    public ResponseEntity<List<Semester>> getCategories() {
        List<Category> listCat= categoryService.getAll();
        List<Semester> listSemester=getAllSemester();
        for (Semester s : listSemester) {
            s.setListCat(listCat);
        }
        return new ResponseEntity<>(listSemester, HttpStatus.OK);
    }


    @GetMapping("/semester/{id}")
    public Semester getSemester(@PathVariable int id) {
        return this.semesterService.getById(id);
    }

    @PostMapping("/semester")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<Semester> createSemester() {
        return new ResponseEntity<>(this.semesterService.addSemester(), HttpStatus.CREATED);
    }
}
