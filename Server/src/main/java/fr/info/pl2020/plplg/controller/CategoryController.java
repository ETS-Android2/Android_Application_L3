package fr.info.pl2020.plplg.controller;

import fr.info.pl2020.plplg.dto.CategoryRequest;
import fr.info.pl2020.plplg.entity.Category;
import fr.info.pl2020.plplg.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = "/category", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Category> getAllCategory() {
        return this.categoryService.getAll();
    }

    @GetMapping(value = "/category/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Category getCategory(@PathVariable int id) {
        return this.categoryService.getById(id);
    }

    @PostMapping(value = "/category", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<Category> createCategory(@RequestBody @Valid CategoryRequest category) {
        return new ResponseEntity<>(this.categoryService.addCategory(category.getName()), HttpStatus.CREATED);
    }
}
