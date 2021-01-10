package guru.springframework.recipeapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
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
	void getRecipes() {
		Recipe recipe = new Recipe();
		Set<Recipe> recipeData = new HashSet<>();
		recipeData.add(recipe);
		
		when(recipeRepository.findAll()).thenReturn(recipeData);
		
		Set<Recipe> recipes = recipeServiceImpl.getRecipes();
		assertEquals(1, recipes.size());
		verify(recipeRepository, times(1)).findAll();
	}

	@Test
	void getRecipeById() {
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		when(recipeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(recipe));
		
		Recipe recipeReturned = recipeServiceImpl.findById(1L);
		assertNotNull(recipeReturned);
		verify(recipeRepository, times(1)).findById(Mockito.anyLong());
		verify(recipeRepository, never()).findAll();
		
	}
}
