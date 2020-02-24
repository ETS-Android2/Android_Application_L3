package fr.info.pl2020.plplg.category;


import fr.info.pl2020.plplg.controller.CategoryController;
import fr.info.pl2020.plplg.entity.Category;
import fr.info.pl2020.plplg.repository.CategoryRepository;
import fr.info.pl2020.plplg.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CategoryService service;

    @MockBean
    CategoryRepository repository;

    @Test
    void getOne() throws Exception {
        Category c = new Category();
        c.setId(1);
        c.setName("math");
        when(service.getById(eq(1))).thenReturn(c);
        this.mockMvc.perform(get("/category/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("math")));
        ;
    }

    @Test
    void getAll() throws Exception {
        Category c = new Category("math");
        c.setId(1);
        when(service.getAll()).thenReturn(Stream.of(c).collect(Collectors.toList()));
        this.mockMvc.perform(get("/category")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("math")));
        ;
    }

    @Test
    void postOne() throws Exception {
        Category c = new Category();
        c.setId(1);
        c.setName("Math");

        when(service.addCategory(anyString())).thenReturn(c);
        this.mockMvc.perform(post("/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Math")));
        ;
    }

    @Test
    void putOne() throws Exception {
        this.mockMvc.perform(put("/category")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void deleteOne() throws Exception {
        this.mockMvc.perform(put("/category/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void deleteAll() throws Exception {
        this.mockMvc.perform(put("/category")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());
    }

}

