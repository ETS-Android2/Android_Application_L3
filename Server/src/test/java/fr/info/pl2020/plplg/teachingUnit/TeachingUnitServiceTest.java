package fr.info.pl2020.plplg.teachingUnit;

import fr.info.pl2020.plplg.entity.Category;
import fr.info.pl2020.plplg.entity.Semester;
import fr.info.pl2020.plplg.entity.TeachingUnit;
import fr.info.pl2020.plplg.exception.ClientRequestException;
import fr.info.pl2020.plplg.repository.CategoryRepository;
import fr.info.pl2020.plplg.repository.SemesterRepository;
import fr.info.pl2020.plplg.repository.TeachingUnitRepository;
import fr.info.pl2020.plplg.service.TeachingUnitService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc()
public class TeachingUnitServiceTest {

    @Mock
    TeachingUnitRepository repository;

    @InjectMocks
    TeachingUnitService service;

    @Test
    public void getByIdTest() {
        TeachingUnit ue = new TeachingUnit();
        ue.setId(1);
        when(this.repository.findById(eq(1))).thenReturn(java.util.Optional.of(ue));

        TeachingUnit teachingUnit = this.service.getById(1);
        assertNotNull(teachingUnit);
        assertEquals(1, teachingUnit.getId());

        assertNull(this.service.getById(2));
    }


    @Test
    public void getAllTest() {
        TeachingUnit ue1 = new TeachingUnit();
        ue1.setId(1);
        TeachingUnit ue2 = new TeachingUnit();
        ue2.setId(2);
        when(this.repository.findAll()).thenReturn(Stream.of(ue1, ue2).collect(Collectors.toList()));

        List<TeachingUnit> teachingUnits = this.service.getAll();
        assertNotNull(teachingUnits);
        assertEquals(2, teachingUnits.size());
        assertEquals(1, teachingUnits.get(0).getId());
        assertEquals(2, teachingUnits.get(1).getId());
    }

    @Test
    public void addTeachingUnitTest() throws ClientRequestException {
        TeachingUnit tu = new TeachingUnit();
        Semester s = new Semester();
        Category c = new Category();
        s.setId(1);
        c.setId(1);
        tu.setId(1);
        tu.setName("a");
        tu.setCode("123");
        tu.setDescription("null");
        tu.setCategory(c);
        tu.setSemester(s);
        when(this.repository.save(any())).thenReturn(tu);
        TeachingUnit teachingUnit = this.service.addTeachingUnit("a", "123", "null", 1, 1);
        assertNotNull(teachingUnit);
        assertEquals(1, teachingUnit.getId());
        assertEquals("a", teachingUnit.getName());
        assertEquals("123", teachingUnit.getCode());
        assertEquals("null", teachingUnit.getDescription());
        assertEquals(s, teachingUnit.getSemester());
        assertEquals(c, teachingUnit.getCategory());
        assertNull(this.service.getById(2));

    }

}
