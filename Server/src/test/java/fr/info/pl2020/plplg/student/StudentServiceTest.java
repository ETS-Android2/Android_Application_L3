package fr.info.pl2020.plplg.student;

import fr.info.pl2020.plplg.entity.Student;
import fr.info.pl2020.plplg.repository.StudentRepository;
import fr.info.pl2020.plplg.service.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc()
public class StudentServiceTest {

    @Mock
    StudentRepository repository;

    @InjectMocks
    StudentService service;

    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    public void getByIdTest() {
        Student s = new Student();
        s.setId(1);
        s.setFirstName("toto");
        s.setLastName("Ben Fredj");
        s.setEmail("nom@unice.fr");
        s.setPassword("1234");
        when(this.repository.findById(eq(1))).thenReturn(java.util.Optional.of(s));
        Student student = this.service.getById(1);
        assertNotNull(student);
        assertEquals(1, student.getId());
        assertEquals("toto", student.getFirstName());
        assertEquals("Ben Fredj", student.getLastName());
        assertEquals("nom@unice.fr", student.getEmail());
        assertEquals("1234", student.getPassword());
        assertNull(this.service.getById(2));
    }

    @Test
    public void getByMailTest() {
        Student s = new Student();
        s.setEmail("toto@unice.fr");
        when(this.repository.findByEmail("toto@unice.fr")).thenReturn(java.util.Optional.of(s));
        Student student = this.service.getByEmail("toto@unice.fr");
        assertNotNull(student);
        assertEquals("toto@unice.fr", student.getEmail());
        assertNull(this.service.getById(2));
    }

    @Test
    public void addStudentTest() {
        Student s = new Student();
        s.setId(1);
        s.setFirstName("toto");
        s.setLastName("nomDeFamille");
        s.setEmail("nom@unice.fr");
        s.setPassword("1234");
        when(this.repository.save(any())).thenReturn(s);
        Student student = this.service.addStudent("toto", "nomDeFamille", "nom@unice.fr", "12345");
        assertNotNull(student);
        assertEquals(1, student.getId());
        assertEquals("toto", student.getFirstName());
        assertEquals("nomDeFamille", student.getLastName());
        assertEquals("nom@unice.fr", student.getEmail());
        assertEquals("1234", student.getPassword());
        assertNull(this.service.getById(2));
    }
}
