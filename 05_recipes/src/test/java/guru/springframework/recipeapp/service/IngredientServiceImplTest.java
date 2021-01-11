package guru.springframework.recipeapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import guru.springframework.recipeapp.commands.IngredientCommand;
import guru.springframework.recipeapp.converters.IngredientToIngredientCommand;
import guru.springframework.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.recipeapp.domain.Ingredient;
import guru.springframework.recipeapp.domain.Recipe;
import guru.springframework.recipeapp.repositories.RecipeRepository;

class IngredientServiceImplTest {
	
	private final IngredientToIngredientCommand ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());

	@Mock private RecipeRepository recipeRepository;
	private IngredientService ingredientService;

	@BeforeEach
	void setUp() throws Exception {
		try(AutoCloseable ac = MockitoAnnotations.openMocks(this)) {
			ingredientService = new IngredientServiceImpl(recipeRepository, ingredientToIngredientCommand);
		}
	}

	@Test
	void testFindByRecipeIdAndIngredientId() {
		// TODO
	}
	
	@Test
	void testFindByRecipeIdAndIngredientIdHappyPath() {
		// Given
		Recipe recipe = Recipe.builder().id(1L).build();
		Set.of(
			Ingredient.builder().id(1L).build(),
			Ingredient.builder().id(2L).build(),
			Ingredient.builder().id(3L).build()
		).forEach(recipe::addIngredient);
		when(recipeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(recipe));

		// When
		IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(1L, 3L);
		
		// Then
		assertEquals(3L, ingredientCommand.getId());
		assertEquals(1L, ingredientCommand.getRecipeId());
		verify(recipeRepository, times(1)).findById(Mockito.anyLong());
	}

}
