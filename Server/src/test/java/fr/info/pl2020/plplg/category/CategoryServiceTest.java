package fr.info.pl2020.plplg.category;

import fr.info.pl2020.plplg.entity.Category;
import fr.info.pl2020.plplg.repository.CategoryRepository;
import fr.info.pl2020.plplg.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CategoryServiceTest {
    @Mock
    CategoryRepository repository;

    @InjectMocks
    CategoryService service;

    @Test
    public void getByIdTest() {
        Category s = new Category("Mathématique");
        s.setId(1);
        when(this.repository.findById(eq(1))).thenReturn(java.util.Optional.of(s));

        Category category = this.service.getById(1);
        assertNotNull(category);
        assertEquals(1, category.getId());
        assertEquals("Mathématique", category.getName());

        assertNull(this.service.getById(2));
    }

    @Test
    public void getAllTest() {
        Category c1 = new Category("Mathématique");
        c1.setId(1);
        Category c2 = new Category("Informatique");
        c2.setId(2);
        Category c3 = new Category("Physique");
        c3.setId(3);
        when(this.repository.findAll()).thenReturn(Stream.of(c1, c2, c3).collect(Collectors.toList()));

        List<Category> categorys = this.service.getAll();
        assertNotNull(categorys);
        assertEquals(3, categorys.size());

        assertEquals(1, categorys.get(0).getId());
        assertEquals("Mathématique", categorys.get(0).getName());

        assertEquals(2, categorys.get(1).getId());
        assertEquals("Informatique", categorys.get(1).getName());

        assertEquals(3, categorys.get(2).getId());
        assertEquals("Physique", categorys.get(2).getName());
        
    }
}
