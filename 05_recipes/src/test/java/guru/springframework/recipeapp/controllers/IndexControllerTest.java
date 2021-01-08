package guru.springframework.recipeapp.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import guru.springframework.recipeapp.domain.Recipe;
import guru.springframework.recipeapp.service.RecipeService;

class IndexControllerTest {
	
	private IndexController indexController;
	@Mock private RecipeService recipeService;
	@Mock private Model model;

	@BeforeEach
	void setUp() throws Exception {
		try (AutoCloseable ac = MockitoAnnotations.openMocks(this)) {
			indexController = new IndexController(recipeService);
		}
	}

	@Test
	void testGetIndexPage() {
		Recipe recipe = new Recipe();
		Set<Recipe> recipeData = new HashSet<>();
		recipeData.add(recipe);
		Mockito.when(recipeService.getRecipes()).thenReturn(recipeData);
		
		String result = indexController.getIndexPage(model);
		
		assertEquals("index", result);
		Mockito.verify(recipeService, Mockito.times(1)).getRecipes();
		Mockito.verify(model, Mockito.times(1)).addAttribute(Mockito.eq("recipes"), Mockito.anySet());
	}

}
