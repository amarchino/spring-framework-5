package guru.springframework.recipeapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import guru.springframework.recipeapp.domain.Recipe;
import guru.springframework.recipeapp.repositories.RecipeRepository;

class RecipeServiceImplTest {
	RecipeServiceImpl recipeServiceImpl;
	@Mock RecipeRepository recipeRepository;
	
	@BeforeEach
	public void setUp() throws Exception {
		try(AutoCloseable mocks = MockitoAnnotations.openMocks(this)) {
			recipeServiceImpl = new RecipeServiceImpl(recipeRepository);
		}
	}

	@Test
	void testGetRecipes() {
		Recipe recipe = new Recipe();
		Set<Recipe> recipeData = new HashSet<>();
		recipeData.add(recipe);
		
		when(recipeRepository.findAll()).thenReturn(recipeData);
		
		Set<Recipe> recipes = recipeServiceImpl.getRecipes();
		assertEquals(1, recipes.size());
		verify(recipeRepository, times(1)).findAll();
	}

}
