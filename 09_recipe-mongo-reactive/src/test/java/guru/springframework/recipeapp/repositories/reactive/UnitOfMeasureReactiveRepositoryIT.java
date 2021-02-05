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

import guru.springframework.recipeapp.domain.UnitOfMeasure;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class UnitOfMeasureReactiveRepositoryIT {

	@Autowired private UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;
	
	@BeforeEach
	public void setUp() throws InterruptedException {
		unitOfMeasureReactiveRepository.deleteAll().block();
	}
	
	@Test
	public void save() throws Exception {
		// given
		unitOfMeasureReactiveRepository.save(UnitOfMeasure.builder().description("UOM 1").build()).block();
		// when
		Long count = unitOfMeasureReactiveRepository.count().block();
		// then
		assertEquals(1, count);
	}
	
	@Test
	public void fetchAll() throws Exception {
		unitOfMeasureReactiveRepository.saveAll(Set.of(
			UnitOfMeasure.builder().description("UOM 1").build(),
			UnitOfMeasure.builder().description("UOM 2").build(),
			UnitOfMeasure.builder().description("UOM 3").build()
		)).blockLast();
		
		AtomicInteger ai = new AtomicInteger();
		CountDownLatch countDownLatch = new CountDownLatch(1);
		unitOfMeasureReactiveRepository.findAll()
		.doOnComplete(countDownLatch::countDown)
		.subscribe(c -> ai.incrementAndGet());
		countDownLatch.await();
		assertEquals(3, ai.get());
	}
	
	@Test
	public void findByDescriptionOk() throws Exception {
		UnitOfMeasure unitOfMeasureSaved = unitOfMeasureReactiveRepository.save(UnitOfMeasure.builder().description("UOM 1").build()).block();
		assertNotNull(unitOfMeasureSaved);

		String description = "UOM 1";
		UnitOfMeasure unitOfMeasure = unitOfMeasureReactiveRepository.findByDescription(description).block();
		assertNotNull(unitOfMeasure);
		assertEquals(description, unitOfMeasure.getDescription());
		assertEquals(unitOfMeasureSaved.getId(), unitOfMeasure.getId());
	}
	
	@Test
	public void findByDescriptionKo() throws Exception {
		UnitOfMeasure unitOfMeasureSaved = unitOfMeasureReactiveRepository.save(UnitOfMeasure.builder().description("UOM 1").build()).block();
		assertNotNull(unitOfMeasureSaved);

		String description = "TEST";
		UnitOfMeasure unitOfMeasure = unitOfMeasureReactiveRepository.findByDescription(description).block();
		assertNull(unitOfMeasure);
	}
	
}
