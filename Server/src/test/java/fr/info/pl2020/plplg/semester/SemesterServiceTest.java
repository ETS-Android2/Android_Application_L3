package fr.info.pl2020.plplg.semester;

import fr.info.pl2020.plplg.entity.Semester;
import fr.info.pl2020.plplg.entity.Student;
import fr.info.pl2020.plplg.repository.SemesterRepository;
import fr.info.pl2020.plplg.service.SemesterService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SemesterServiceTest {

    @Mock
    SemesterRepository repository;

    @InjectMocks
    SemesterService service;

    @Test
    public void getByIdTest() {
        Semester s = new Semester();
        s.setId(1);
        when(this.repository.findById(eq(1))).thenReturn(java.util.Optional.of(s));

        Semester semester = this.service.getById(1);
        assertNotNull(semester);
        assertEquals(1, semester.getId());

        assertNull(this.service.getById(2));
    }

    @Test
    public void getAllTest() {
        Semester s1 = new Semester();
        s1.setId(1);
        Semester s2 = new Semester();
        s2.setId(2);
        when(this.repository.findAll()).thenReturn(Stream.of(s1, s2).collect(Collectors.toList()));

        List<Semester> semesters = this.service.getAll();
        assertNotNull(semesters);
        assertEquals(2, semesters.size());
        assertEquals(1, semesters.get(0).getId());
        assertEquals(2, semesters.get(1).getId());
    }

    @Test
    public void addSemesterTest() {
        Semester s = new Semester();
        s.setId(1);
        when(this.repository.save(any())).thenReturn(s);
        Semester semester = this.service.addSemester();
        assertNotNull(semester);
        assertEquals(1, semester.getId());
        assertNull(this.service.getById(2));

    }


}
