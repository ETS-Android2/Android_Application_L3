package fr.info.pl2020.plplg.student;

import fr.info.pl2020.plplg.entity.Category;
import fr.info.pl2020.plplg.entity.Semester;
import fr.info.pl2020.plplg.entity.Student;
import fr.info.pl2020.plplg.entity.TeachingUnit;
import fr.info.pl2020.plplg.exception.ClientRequestException;
import fr.info.pl2020.plplg.repository.StudentRepository;
import fr.info.pl2020.plplg.repository.TeachingUnitRepository;
import fr.info.pl2020.plplg.service.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.query.ExtensionAwareQueryMethodEvaluationContextProvider;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc()
public class StudentServiceTest {
    @Mock
    TeachingUnitRepository teachingUnitRepository;

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

    @Test
    public void addTeachingUnitInCareerTest() throws ClientRequestException {

        //list Teachingunit 1
        Semester semester = new Semester();
        semester.setYear(1);
        TeachingUnit tu = new TeachingUnit("UE1", "1", "blablabla", semester, new Category());
        tu.setId(1);
        List<TeachingUnit> listTu1 = new ArrayList<>();
        List<Integer> listIdTu1 = new ArrayList<>();
        listTu1.add(tu);
        listIdTu1.add(tu.getId());

        //list TeachingUnit 2
        List<TeachingUnit> listTu2 = listTu1;
        List<Integer> listIdTu2 = listIdTu1;
        listTu2.add(new TeachingUnit("UE2", "2", "blablabla", semester, new Category()));
        listIdTu2.add(2);

        //student
        Student s = new Student();
        s.setId(1);
        s.setFirstName("toto");
        s.setLastName("nomDeFamille");
        s.setEmail("nom@unice.fr");
        s.setPassword("1234");
        s.setCareer(listTu1);

        when(this.repository.save(any())).thenReturn(s);
        when(this.teachingUnitRepository.findAllByIdIn(any())).thenReturn(listTu2);
        when(this.repository.findById(any())).thenReturn(java.util.Optional.of(s));
        this.service.updateCareer(s, listIdTu2);
        assertNotNull(s);
        assertEquals(1, s.getId());
        assertEquals(listTu2, s.getCareer());
        assertNotNull(this.service.getById(1));
    }

    @Test
    public void checkPrerequisiteTeachingUnitsTest() throws ClientRequestException {
        //list Teachingunit 1
        Semester semester = new Semester();
        Category category = new Category();
        category.setName("math");
        category.setId(1);
        semester.setYear(1);
        TeachingUnit tu = new TeachingUnit("UE1", "1", "blablabla", semester, category);
        tu.setId(1);
        List<TeachingUnit> listTu1 = new ArrayList<>();
        List<Integer> listIdTu1 = new ArrayList<>();
        listTu1.add(tu);
        listIdTu1.add(tu.getId());

        //list TeachingUnit 2
        Semester semester3 = new Semester();
        Category category2 = new Category();
        category2.setId(2);
        category2.setName("info");
        semester3.setYear(2);
        List<TeachingUnit> listTu2 = listTu1;
        List<Integer> listIdTu2 = listIdTu1;
        listTu2.add(new TeachingUnit("UE2", "2", "blablabla", semester3, category2));
        listIdTu2.add(2);
        try {
            service.checkPrerequisiteTeachingUnits(listTu1, listTu2);
        } catch (ClientRequestException Exp) {
            assertNotNull(Exp.getClientMessage());
            assert (Exp.getClientMessage().contains("{\"error\":\"" + "Vous devez avoir validé une UE de la catégorie " + category2.getName() + " à la " + semester.getYear() + "ère" + " année." + "\"}"));
        }

    }
    @Test
    public void  maxiTeachingUnitTest() throws ClientRequestException {
        //list Teachingunit
        Semester semester = new Semester();
        Category category = new Category();
        category.setName("math");
        category.setId(1);
        semester.setYear(1);
        semester.setId(1);
        List<TeachingUnit> listTu = new ArrayList<>();
        listTu.add(new TeachingUnit("UE1", "1", "blablabla", semester, category));
        listTu.add( new TeachingUnit("UE2","2","blablabla",semester,category));
        listTu.add( new TeachingUnit("UE3","3","blablabla",semester,category));
        listTu.add( new TeachingUnit("UE4","4","blablabla",semester,category));
        listTu.add( new TeachingUnit("UE5","5","blablabla",semester,category));
        try {
            service.maxiTeachingUnit(listTu);
        } catch (ClientRequestException Exp) {
            assertNotNull(Exp.getClientMessage());
            assert (Exp.getClientMessage().contains("{\"error\":\"" + "Vous ne pouvez pas sélectionner plus que quatre UE par semestre." + "\"}"));
        }
    }



}
