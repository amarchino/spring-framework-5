package guru.springframework.recipeapp.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CategoryTest {
	
	private Category category;
	
	@BeforeEach
	public void setUp() {
		category = new Category();
	}

	@Test
	void testGetId() {
		String id = "4";
		category.setId(id);
		assertEquals(id, category.getId());
	}

	@Test
	void testGetDescription() {
		String description = "Test description";
		category.setDescription(description);
		assertEquals(description, category.getDescription());
	}

}
