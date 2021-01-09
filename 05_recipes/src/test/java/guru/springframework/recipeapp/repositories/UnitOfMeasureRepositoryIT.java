package guru.springframework.recipeapp.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import guru.springframework.recipeapp.domain.UnitOfMeasure;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UnitOfMeasureRepositoryIT {
	
	@Autowired UnitOfMeasureRepository unitOfMeasureRepository;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testFindByDescription() {
		Optional<UnitOfMeasure> uom = unitOfMeasureRepository.findByDescription("Teaspoon");
		assertTrue(uom.isPresent());
		assertEquals("Teaspoon", uom.get().getDescription());
	}
	
	@Test
	void testFindByDescriptionCup() {
		String unitOfMeasure = "Cup";
		Optional<UnitOfMeasure> uom = unitOfMeasureRepository.findByDescription(unitOfMeasure);
		assertTrue(uom.isPresent());
		assertEquals(unitOfMeasure, uom.get().getDescription());
	}
	
	@Test
	void testFindByDescriptionNotFound() {
		Optional<UnitOfMeasure> uom = unitOfMeasureRepository.findByDescription("Teaspoons");
		assertFalse(uom.isPresent());
	}
	@Test
	
	void testFindByDescriptionNotFoundException() {
		Optional<UnitOfMeasure> uom = unitOfMeasureRepository.findByDescription("Teaspoons");
		assertThrows(NoSuchElementException.class, () -> uom.get());
	}

}
