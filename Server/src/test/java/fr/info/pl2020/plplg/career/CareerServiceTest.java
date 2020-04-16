package fr.info.pl2020.plplg.career;

import fr.info.pl2020.plplg.entity.Career;
import fr.info.pl2020.plplg.entity.Category;
import fr.info.pl2020.plplg.entity.Semester;
import fr.info.pl2020.plplg.entity.TeachingUnit;
import fr.info.pl2020.plplg.exception.ClientRequestException;
import fr.info.pl2020.plplg.repository.CareerRepository;
import fr.info.pl2020.plplg.repository.TeachingUnitRepository;
import fr.info.pl2020.plplg.service.CareerService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc()
public class CareerServiceTest {

    @InjectMocks
    CareerService careerService;

    @Mock
    TeachingUnitRepository teachingUnitRepository;

    @Mock
    CareerRepository careerRepository;

    @Test
    public void addTeachingUnitInCareerTest() throws Exception {
        Semester semester = new Semester();
        semester.setId(1);
        semester.setYear(1);

        //list avant update
        TeachingUnit tu = new TeachingUnit("UE1", "1", "blablabla", semester, new Category());
        tu.setId(1);
        List<TeachingUnit> listTu1 = new ArrayList<>(Collections.singletonList(tu));

        //list après update
        TeachingUnit tu2 = new TeachingUnit("UE2", "2", "blablabla", semester, new Category());
        tu2.setId(2);
        List<TeachingUnit> listTu2 = new ArrayList<>(listTu1);
        listTu2.add(new TeachingUnit("UE2", "2", "blablabla", semester, new Category()));

        Career career = new Career("Mon parcours", listTu1, false, false);
        Career expectedCareer = new Career("Mon parcours", listTu2, false, false);

        when(this.teachingUnitRepository.findAllByIdIn(any())).thenReturn(new ArrayList<>(Collections.singletonList(tu)));
        when(this.careerRepository.save(any())).thenReturn(expectedCareer);

        this.careerService.updateCareer(career, Arrays.asList(1, 2), 1);
    }
/*
    // Tester le fait que les Ues s'ajjoute et ne se supprime pas si on modifie la liste ValidateCareer
    @Test
    public void addTeachingUnitInValidateCareerTest() throws ClientRequestException {

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
        List<TeachingUnit> listTu2 = new ArrayList<>();
        List<Integer> listIdTu2 = new ArrayList<>();
        listTu2.add(new TeachingUnit("UE2", "2", "blablabla", semester, new Category()));
        listTu2.add(new TeachingUnit("UE3", "3", "blablabla", semester, new Category()));
        listIdTu2.add(2);
        listIdTu2.add(3);
        //list TeachingUnit 3
        List<TeachingUnit> listTu3 = listTu2;
        List<Integer> listIdTu3 = listIdTu2;
        listTu3.addAll(listTu1);
        listIdTu3.addAll(listIdTu1);

        //student
        Student s = new Student();
        s.setId(1);
        s.setFirstName("toto");
        s.setLastName("nomDeFamille");
        s.setEmail("nom@unice.fr");
        s.setPassword("1234");
        s.setCareer(listTu1);
        s.setValidateCareer(listTu2);
        when(this.repository.save(any())).thenReturn(s);
        when(this.repository.findById(any())).thenReturn(java.util.Optional.of(s));
        this.service.validateCareer(s, listIdTu1);
        assertNotNull(s);
        assertEquals(1, s.getId());
        assertEquals(listTu3, s.getValidateCareer());
        assertNotNull(this.service.getById(1));
    }

    // on test si lorsque l'utilisateur supprime une Ue et update son parcours, elle est bien supprimer
    @Test
    public void removeTeachingUnitInCareerTest() throws ClientRequestException {

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
        listTu2.add(new TeachingUnit("UE3", "3", "blablabla", semester, new Category()));
        listIdTu2.add(2);
        listIdTu2.add(3);
        //student
        Student s = new Student();
        s.setId(1);
        s.setFirstName("toto");
        s.setLastName("nomDeFamille");
        s.setEmail("nom@unice.fr");
        s.setPassword("1234");
        s.setCareer(listTu2);
        this.service.updateCareer(s, listIdTu2, 1);
        when(this.repository.save(any())).thenReturn(s);
        when(this.repository.findById(any())).thenReturn(java.util.Optional.of(s));
        this.service.updateCareer(s, listIdTu1, 1);
        assertNotNull(this.service.getById(1));
        assertNotNull(s);
        assertEquals(1, s.getId());
        assertEquals(listTu1, s.getCareer());

    }
*/

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
        assertThrows(ClientRequestException.class, () -> this.careerService.checkPrerequisiteTeachingUnits(listTu1, listTu2)); //TODO tester le cas ou ça passe bien
    }

    @Test
    public void maxiTeachingUnitTest() throws ClientRequestException {
        //list Teachingunit
        Semester semester = new Semester();
        Category category = new Category();
        category.setName("math");
        category.setId(1);
        semester.setYear(1);
        semester.setId(1);
        List<TeachingUnit> listTu = new ArrayList<>();
        listTu.add(new TeachingUnit("UE1", "1", "blablabla", semester, category));
        listTu.add(new TeachingUnit("UE2", "2", "blablabla", semester, category));
        listTu.add(new TeachingUnit("UE3", "3", "blablabla", semester, category));
        listTu.add(new TeachingUnit("UE4", "4", "blablabla", semester, category));
        listTu.add(new TeachingUnit("UE5", "5", "blablabla", semester, category));
        assertThrows(ClientRequestException.class, () -> this.careerService.maxiTeachingUnit(listTu)); //TODO tester le cas ou ça passe bien
    }

}
