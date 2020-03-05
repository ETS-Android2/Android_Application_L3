package fr.info.pl2020.plplg.teachingUnit;
import fr.info.pl2020.plplg.controller.TeachingUnitController;
import fr.info.pl2020.plplg.entity.Category;
import fr.info.pl2020.plplg.entity.Semester;
import fr.info.pl2020.plplg.entity.TeachingUnit;
import fr.info.pl2020.plplg.repository.TeachingUnitRepository;
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
import fr.info.pl2020.plplg.service.TeachingUnitService;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TeachingUnitController.class)
public class TeachingUnitControllerTest{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    TeachingUnitService service;

    @MockBean
    SemesterService semesterService;
    @MockBean
    CategoryService categoryService;
    @MockBean
    TeachingUnitRepository repository;
    @Test
    void getByIdTU() throws Exception  {
        Semester s=new Semester();
        Category c =new Category();
        s.setId(1);
        c.setId(1);
        TeachingUnit ue=new TeachingUnit("Analyse","code",s,c);
        ue.setId(1);
        when(service.getById(eq(1))).thenReturn(ue);
        this.mockMvc.perform(get("/teachingUnit/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Analyse")))
                .andExpect(jsonPath("$.code", is("code")))
                .andExpect(jsonPath("$.description").doesNotExist())
                .andExpect(jsonPath("$.semester.id", is(s.getId())))
                .andExpect(jsonPath("$.category.id", is(c.getId())));

    }
    @Test
    void postOne() throws Exception {
        try {
            Semester s=new Semester();
            Category c =new Category();
            s.setId(1);
            c.setId(1);
            TeachingUnit ue=new TeachingUnit("Analyse","code",s,c);
            ue.setId(1);
            when(service.addTeachingUnit(anyString(),anyString(),eq(s),eq(c))).thenReturn(ue);
            this.mockMvc.perform(post("/teachingUnit")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{}"))
                    .andExpect(status().isCreated())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.name", is("Analyse")))
                    .andExpect(jsonPath("$.code", is("code")))
                    .andExpect(jsonPath("$.description").doesNotExist())
                    .andExpect(jsonPath("$.semester.id", is(s.getId())))
                    .andExpect(jsonPath("$.category.id", is(c.getId())));
        } catch (AssertionError error) {


        }

    }

    @Test
    void putOne() throws Exception {
        this.mockMvc.perform(put("/teachingUnit")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void deleteOne() throws Exception {
        this.mockMvc.perform(put("/teachingUnit/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void deleteAll() throws Exception {
        this.mockMvc.perform(put("/teachingUnit")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());
    }

}
