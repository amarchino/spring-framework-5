package guru.springframework.recipeapp.repositories.reactive;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import guru.springframework.recipeapp.domain.Category;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class CategoryReactiveRepositoryIT {

	@Autowired private CategoryReactiveRepository categoryReactiveRepository;
	
	@BeforeEach
	public void setUp() throws Exception {
		categoryReactiveRepository.deleteAll().block();
	}
	
	@Test
	public void save() throws Exception {
		// given
		categoryReactiveRepository.save(Category.builder().description("Category 1").build()).block();
		// when
		Long count = categoryReactiveRepository.count().block();
		// then
		assertEquals(1, count);
	}
	
	@Test
	public void fetchAll() throws Exception {
		categoryReactiveRepository.saveAll(Set.of(
			Category.builder().description("Category 1").build(),
			Category.builder().description("Category 2").build(),
			Category.builder().description("Category 3").build()
		)).blockLast();
		
		AtomicInteger ai = new AtomicInteger();
		CountDownLatch countDownLatch = new CountDownLatch(1);
		categoryReactiveRepository.findAll()
		.doOnComplete(countDownLatch::countDown)
		.subscribe(c -> ai.incrementAndGet());
		countDownLatch.await();
		assertEquals(3, ai.get());
	}
	
	@Test
	public void findByDescriptionOk() throws Exception {
		Category categorySaved = categoryReactiveRepository.save(Category.builder().description("Category 1").build()).block();
		assertNotNull(categorySaved);

		String description = "Category 1";
		Category category = categoryReactiveRepository.findByDescription(description).block();
		assertNotNull(category);
		assertEquals(description, category.getDescription());
		assertEquals(categorySaved.getId(), category.getId());
	}
	
	@Test
	public void findByDescriptionKo() throws Exception {
		Category categorySaved = categoryReactiveRepository.save(Category.builder().description("Category 1").build()).block();
		assertNotNull(categorySaved);

		String description = "TEST";
		Category category = categoryReactiveRepository.findByDescription(description).block();
		assertNull(category);
	}
	
}
