package guru.springframework.recipeapp.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
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
		// Given 
		Set<Recipe> recipeData = new HashSet<>();
		recipeData.add(new Recipe());
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		recipeData.add(recipe);
		Mockito.when(recipeService.getRecipes()).thenReturn(recipeData);
		@SuppressWarnings("unchecked")
		ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);
		
		// When
		String result = indexController.getIndexPage(model);
		
		// Then
		assertEquals("index", result);
		Mockito.verify(recipeService, Mockito.times(1)).getRecipes();
		Mockito.verify(model, Mockito.times(1)).addAttribute(Mockito.eq("recipes"), argumentCaptor.capture());
		Set<Recipe> setInController = argumentCaptor.getValue();
		assertEquals(2, setInController.size());
	}

}
