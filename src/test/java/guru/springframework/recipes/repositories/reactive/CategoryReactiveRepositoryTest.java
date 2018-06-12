package guru.springframework.recipes.repositories.reactive;

import guru.springframework.recipes.domain.Category;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataMongoTest
public class CategoryReactiveRepositoryTest {

    @Autowired
    CategoryReactiveRepository categoryReactiveRepository;

    @Before
    public void setUp() throws Exception {
        categoryReactiveRepository.deleteAll().block();
    }

    @Test
    public void testSave() throws Exception {
        Category category = new Category();
        category.setDescription("American");
        categoryReactiveRepository.save(category).block();
        Long count = categoryReactiveRepository.count().block();
        assertEquals(Long.valueOf(1L), count);
    }

    @Test
    public void findByDescription() {
        Category category1 = new Category();
        category1.setDescription("American");
        Category category2 = new Category();
        category2.setDescription("Mexican");
        categoryReactiveRepository.save(category1).block();
        categoryReactiveRepository.save(category2).block();
        Category savedCategory = categoryReactiveRepository.findByDescription("American").block();
        assertNotNull(savedCategory.getId());
    }
}