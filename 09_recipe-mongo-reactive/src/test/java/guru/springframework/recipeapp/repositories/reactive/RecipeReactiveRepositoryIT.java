package guru.springframework.recipeapp.repositories.reactive;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import guru.springframework.recipeapp.domain.Recipe;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class RecipeReactiveRepositoryIT {

	@Autowired private RecipeReactiveRepository recipeReactiveRepository;
	
	@BeforeEach
	public void setUp() throws Exception {
		recipeReactiveRepository.deleteAll().block();
	}
	
	@Test
	public void save() throws Exception {
		// given
		recipeReactiveRepository.save(Recipe.builder().description("Recipe 1").build()).block();
		// when
		Long count = recipeReactiveRepository.count().block();
		// then
		assertEquals(1, count);
	}
	
	@Test
	public void fetchAll() throws Exception {
		recipeReactiveRepository.saveAll(Set.of(
			Recipe.builder().description("Recipe 1").build(),
			Recipe.builder().description("Recipe 2").build(),
			Recipe.builder().description("Recipe 3").build()
		)).blockLast();
		
		AtomicInteger ai = new AtomicInteger();
		CountDownLatch countDownLatch = new CountDownLatch(1);
		recipeReactiveRepository.findAll()
		.doOnComplete(countDownLatch::countDown)
		.subscribe(c -> ai.incrementAndGet());
		countDownLatch.await();
		assertEquals(3, ai.get());
		
	}
	
}
