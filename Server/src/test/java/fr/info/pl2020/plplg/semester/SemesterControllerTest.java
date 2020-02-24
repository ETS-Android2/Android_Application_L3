package fr.info.pl2020.plplg.semester;

import fr.info.pl2020.plplg.controller.SemesterController;
import fr.info.pl2020.plplg.entity.Category;
import fr.info.pl2020.plplg.entity.Semester;
import fr.info.pl2020.plplg.repository.SemesterRepository;
import fr.info.pl2020.plplg.service.CategoryService;
import fr.info.pl2020.plplg.service.SemesterService;
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
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SemesterController.class)
public class SemesterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    SemesterService service;

    @MockBean
    SemesterRepository repository;

    @MockBean
    CategoryService categoryService;

    @Test
    void getOne() throws Exception {
        Semester s = new Semester();
        s.setId(1);
        when(service.getById(eq(1))).thenReturn(s);
        this.mockMvc.perform(get("/semester/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void getAll() throws Exception {
        Semester s = new Semester();
        s.setId(1);
        when(service.getAll()).thenReturn(Stream.of(s).collect(Collectors.toList()));
        this.mockMvc.perform(get("/semester")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", is(1)));
    }

    @Test
    void postOne() throws Exception {
        Semester s = new Semester();
        s.setId(1);
        when(service.addSemester()).thenReturn(s);
        this.mockMvc.perform(post("/semester")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void putOne() throws Exception {
        this.mockMvc.perform(put("/semester")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void deleteOne() throws Exception {
        this.mockMvc.perform(put("/semester/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void deleteAll() throws Exception {
        this.mockMvc.perform(put("/semester")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void getCategories() throws Exception {
        Semester s = new Semester();
        s.setId(1);
        Category c = new Category("Mathématique");
        c.setId(1);
        when(service.getAll()).thenReturn(Stream.of(s).collect(Collectors.toList()));
        when(categoryService.getAll()).thenReturn(Stream.of(c).collect(Collectors.toList()));
        this.mockMvc.perform(get("/semesterAll")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].listCat[0].name", is("Mathématique")))
                .andExpect(jsonPath("$[0].listCat[0].id", is(1)));


    }
    @Test
    void getCategoriesNull() throws Exception {
        Semester s = new Semester();
        s.setId(1);
        when(service.getAll()).thenReturn(Stream.of(s).collect(Collectors.toList()));
        when(categoryService.getAll()).thenReturn(null);
        this.mockMvc.perform(get("/semesterAll")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].listCat").doesNotExist());

    }
}
