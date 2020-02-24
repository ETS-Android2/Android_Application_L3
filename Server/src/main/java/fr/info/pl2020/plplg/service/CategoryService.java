package fr.info.pl2020.plplg.service;

import fr.info.pl2020.plplg.entity.Category;
import fr.info.pl2020.plplg.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAll() {
        return this.categoryRepository.findAll();
    }

    public Category getById(int id) {
        return this.categoryRepository.findById(id).orElse(null);
    }

    public Category addCategory(String name){
        Category c = new Category(name);
        return this.categoryRepository.save(c);
    }

}
