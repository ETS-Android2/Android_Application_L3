package fr.info.pl2020.plplg.student;

import fr.info.pl2020.plplg.entity.Student;
import fr.info.pl2020.plplg.entity.TeachingUnit;
import fr.info.pl2020.plplg.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest()
@AutoConfigureMockMvc()
@WithMockUser
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    StudentService service;

    @Test
    void getStudentTest() throws Exception {
        Student s = new Student("Yasmine", "Ben Fredj", "yas@mine.fr", "1234");
        s.setId(1);
        when(service.getById(eq(1))).thenReturn(s);
        this.mockMvc.perform(get("/student")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Yasmine")))
                .andExpect(jsonPath("$.lastName", is("Ben Fredj")))
                .andExpect(jsonPath("$.email", is("yas@mine.fr")))
                .andExpect(jsonPath("$.password", is("1234")))
                .andExpect(jsonPath("$.career").isEmpty());
    }

    @Test
    void postOne() throws Exception {
        Student s = new Student();
        s.setId(1);
        s.setFirstName("Yasmine");
        s.setLastName("Ben Fredj");
        s.setEmail("yas@mine.fr");
        s.setPassword("1234");
        when(service.addStudent(anyString(), anyString(), anyString(), anyString())).thenReturn(s);
        this.mockMvc.perform(post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"firstName\": \"Yasmine\", \"lastName\": \"Ben Fredj\", \"email\": \"yas@mine.fr\", \"password\": \"1234\"}")
                .characterEncoding("utf-8"))
                //.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Yasmine")))
                .andExpect(jsonPath("$.lastName", is("Ben Fredj")))
                .andExpect(jsonPath("$.email", is("yas@mine.fr")))
                .andExpect(jsonPath("$.password", is("1234")));

    }

    @Test
    void postOneEmptyBody() throws Exception {
        this.mockMvc.perform(post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{}")
                .characterEncoding("utf-8"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void postOneMissingName() throws Exception {
        this.mockMvc.perform(post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"lastName\": \"Ben Fredj\", \"email\": \"yas@mine.fr\", \"password\": \"1234\"}")
                .characterEncoding("utf-8"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void putOne() throws Exception {
        this.mockMvc.perform(put("/student/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteOne() throws Exception {
        this.mockMvc.perform(delete("/student/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteAll() throws Exception {
        this.mockMvc.perform(delete("/student")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());
    }
}
