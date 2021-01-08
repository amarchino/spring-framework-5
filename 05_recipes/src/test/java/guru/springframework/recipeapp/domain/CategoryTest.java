package guru.springframework.recipeapp.domain;

import static org.junit.jupiter.api.Assertions.*;

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
		Long id = 4L;
		category.setId(id);
		assertEquals(id, category.getId());
	}

	@Test
	void testGetDescription() {
		fail("Not yet implemented");
	}

	@Test
	void testGetRecipes() {
		fail("Not yet implemented");
	}

}
