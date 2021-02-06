package guru.springframework.recipeapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import guru.springframework.recipeapp.converters.RecipeCommandToRecipe;
import guru.springframework.recipeapp.converters.RecipeToRecipeCommand;
import guru.springframework.recipeapp.domain.Recipe;
import guru.springframework.recipeapp.exceptions.NotFoundException;
import guru.springframework.recipeapp.repositories.reactive.RecipeReactiveRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

class RecipeServiceImplTest {
	private RecipeServiceImpl recipeServiceImpl;
	@Mock private RecipeReactiveRepository recipeReactiveRepository;
	@Mock private RecipeToRecipeCommand recipeToRecipeCommand;
    @Mock private RecipeCommandToRecipe recipeCommandToRecipe;
	
	@BeforeEach
	public void setUp() throws Exception {
		try(AutoCloseable mocks = MockitoAnnotations.openMocks(this)) {
			recipeServiceImpl = new RecipeServiceImpl(recipeReactiveRepository, recipeCommandToRecipe, recipeToRecipeCommand);
		}
	}

	@Test
	void getRecipes() {
		when(recipeReactiveRepository.findAll()).thenReturn(Flux.just(Recipe.builder().build()));
		
		List<Recipe> recipes = recipeServiceImpl.getRecipes().collectList().block();
		assertEquals(1, recipes.size());
		verify(recipeReactiveRepository, times(1)).findAll();
	}

	@Test
	void getRecipeById() {
		when(recipeReactiveRepository.findById(Mockito.anyString())).thenReturn(Mono.just(Recipe.builder().id("1").build()));
		
		Recipe recipeReturned = recipeServiceImpl.findById("1").block();
		assertNotNull(recipeReturned);
		verify(recipeReactiveRepository, times(1)).findById(Mockito.anyString());
		verify(recipeReactiveRepository, never()).findAll();
	}
	
	@Test
	void getRecipeByIdNotFound() {
		when(recipeReactiveRepository.findById(Mockito.anyString())).thenReturn(Mono.empty());
		assertThrows(NotFoundException.class, () -> recipeServiceImpl.findById("1").block());
	}
	
	@Test
	void deleteById() {
		String idToDelete = "1";
		when(recipeReactiveRepository.deleteById(Mockito.anyString())).thenReturn(Mono.empty());
		recipeServiceImpl.deleteById(idToDelete).block();
		
		verify(recipeReactiveRepository, times(1)).deleteById(Mockito.anyString());
	}
}
