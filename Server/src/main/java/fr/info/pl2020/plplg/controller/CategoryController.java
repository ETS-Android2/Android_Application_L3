package fr.info.pl2020.plplg.controller;

import fr.info.pl2020.plplg.entity.Category;
import fr.info.pl2020.plplg.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category")
    public List<Category> getAllCategory() {
            return this.categoryService.getAll();
        }

    @GetMapping("/category/{id}")
    public Category getCategory(@PathVariable int id) {
        return this.categoryService.getById(id);
    }

    @PostMapping("/category")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<Category> createCategory(@RequestBody String name) {
        return new ResponseEntity<>(this.categoryService.addCategory(name), HttpStatus.CREATED);
    }
}
