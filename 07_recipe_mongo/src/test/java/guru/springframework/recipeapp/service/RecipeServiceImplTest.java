package guru.springframework.recipeapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import guru.springframework.recipeapp.converters.RecipeCommandToRecipe;
import guru.springframework.recipeapp.converters.RecipeToRecipeCommand;
import guru.springframework.recipeapp.domain.Recipe;
import guru.springframework.recipeapp.exceptions.NotFoundException;
import guru.springframework.recipeapp.repositories.RecipeRepository;

class RecipeServiceImplTest {
	RecipeServiceImpl recipeServiceImpl;
	@Mock RecipeRepository recipeRepository;
	@Mock RecipeToRecipeCommand recipeToRecipeCommand;
    @Mock RecipeCommandToRecipe recipeCommandToRecipe;
	
	@BeforeEach
	public void setUp() throws Exception {
		try(AutoCloseable mocks = MockitoAnnotations.openMocks(this)) {
			recipeServiceImpl = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
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
		recipe.setId("1");
		when(recipeRepository.findById(Mockito.anyString())).thenReturn(Optional.of(recipe));
		
		Recipe recipeReturned = recipeServiceImpl.findById("1");
		assertNotNull(recipeReturned);
		verify(recipeRepository, times(1)).findById(Mockito.anyString());
		verify(recipeRepository, never()).findAll();
	}
	
	@Test
	void getRecipeByIdNotFound() {
		when(recipeRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
		
		assertThrows(NotFoundException.class, () -> recipeServiceImpl.findById("1"));
	}
	
	@Test
	void deleteById() {
		String idToDelete = "1";
		recipeServiceImpl.deleteById(idToDelete);
		
		verify(recipeRepository, times(1)).deleteById(Mockito.anyString());
	}
}
