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
	
	@Autowired UnitOfMeasureRepository uomOfMeasureRepository;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testFindByDescription() {
		Optional<UnitOfMeasure> uom = uomOfMeasureRepository.findByDescription("Teaspoon");
		assertTrue(uom.isPresent());
		assertEquals("Teaspoon", uom.get().getDescription());
	}
	
	@Test
	void testFindByDescriptionCup() {
		String uomOfMeasure = "Cup";
		Optional<UnitOfMeasure> uom = uomOfMeasureRepository.findByDescription(uomOfMeasure);
		assertTrue(uom.isPresent());
		assertEquals(uomOfMeasure, uom.get().getDescription());
	}
	
	@Test
	void testFindByDescriptionNotFound() {
		Optional<UnitOfMeasure> uom = uomOfMeasureRepository.findByDescription("Teaspoons");
		assertFalse(uom.isPresent());
	}
	@Test
	
	void testFindByDescriptionNotFoundException() {
		Optional<UnitOfMeasure> uom = uomOfMeasureRepository.findByDescription("Teaspoons");
		assertThrows(NoSuchElementException.class, () -> uom.get());
	}

}
