package guru.springframework.recipeapp.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

	@Test
	void testGetRecipes() {
		assertNotNull(category.getRecipes());
		
		Recipe recipe = new Recipe();
		recipe.setId("1");
		category.getRecipes().add(recipe);
		recipe = new Recipe();
		recipe.setId("2");
		category.getRecipes().add(recipe);
		
		assertFalse(category.getRecipes().isEmpty());
		assertEquals(2, category.getRecipes().size());
	}

}
