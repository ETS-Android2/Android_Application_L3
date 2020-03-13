package fr.info.pl2020.plplg.teachingUnit;

import fr.info.pl2020.plplg.controller.TeachingUnitController;
import fr.info.pl2020.plplg.entity.Category;
import fr.info.pl2020.plplg.entity.Semester;
import fr.info.pl2020.plplg.entity.TeachingUnit;
import fr.info.pl2020.plplg.repository.CategoryRepository;
import fr.info.pl2020.plplg.repository.SemesterRepository;
import fr.info.pl2020.plplg.repository.TeachingUnitRepository;
import fr.info.pl2020.plplg.service.CategoryService;
import fr.info.pl2020.plplg.service.SemesterService;
import fr.info.pl2020.plplg.service.StudentService;
import fr.info.pl2020.plplg.service.TeachingUnitService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TeachingUnitController.class)
public class TeachingUnitControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    TeachingUnitService service;

    @MockBean
    SemesterService semesterService;

    @MockBean
    StudentService studentService;

    @MockBean
    CategoryService categoryService;

    @MockBean
    TeachingUnitRepository repository;

    @MockBean
    SemesterRepository semesterRepository;

    @MockBean
    CategoryRepository categoryRepository;

    @Test
    void getByIdTU() throws Exception {
        Semester s = new Semester();
        Category c = new Category();
        s.setId(1);
        c.setId(1);
        TeachingUnit ue = new TeachingUnit("Analyse", "code", "description", s, c);
        ue.setId(1);
        when(service.getById(eq(1))).thenReturn(ue);
        this.mockMvc.perform(get("/teachingUnit/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Analyse")))
                .andExpect(jsonPath("$.code", is("code")))
                .andExpect(jsonPath("$.description", is("description")))
                .andExpect(jsonPath("$.semester.id", is(s.getId())))
                .andExpect(jsonPath("$.category.id", is(c.getId())));

    }

    @Test
    void postOne() throws Exception {
        Semester s = new Semester();
        Category c = new Category();
        s.setId(1);
        c.setId(1);
        TeachingUnit ue = new TeachingUnit("Name", "Code", "Description", s, c);
        ue.setId(1);
        when(service.addTeachingUnit(anyString(), anyString(), anyString(), any(), any())).thenReturn(ue);
        this.mockMvc.perform(post("/teachingUnit")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"code\": \"Code\", \"description\": \"Description\", \"name\": \"Name\", \"semester\": {\"id\": 1}, \"category\": {\"id\": 1}}")
                .characterEncoding("utf-8"))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Name")))
                .andExpect(jsonPath("$.code", is("Code")))
                .andExpect(jsonPath("$.description", is("Description")))
                .andExpect(jsonPath("$.semester.id", is(s.getId())))
                .andExpect(jsonPath("$.category.id", is(c.getId())));

    }

    @Test
    void postOneEmptyBody() throws Exception {
        this.mockMvc.perform(post("/teachingUnit")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{}")
                .characterEncoding("utf-8"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void postOneMissingName() throws Exception {
        this.mockMvc.perform(post("/teachingUnit")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"code\": \"Code\", \"description\": \"Description\", \"semester\": {\"id\": 1}, \"category\": {\"id\": 1}}")
                .characterEncoding("utf-8"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void putOne() throws Exception {
        this.mockMvc.perform(put("/teachingUnit/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void deleteOne() throws Exception {
        this.mockMvc.perform(delete("/teachingUnit/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void deleteAll() throws Exception {
        this.mockMvc.perform(delete("/teachingUnit")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());
    }

}
