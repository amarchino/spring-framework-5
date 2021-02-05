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
import guru.springframework.recipeapp.converters.IngredientCommandToIngredient;
import guru.springframework.recipeapp.converters.IngredientToIngredientCommand;
import guru.springframework.recipeapp.converters.UnitOfMeasureCommandToUnitOfMeasure;
import guru.springframework.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.recipeapp.domain.Ingredient;
import guru.springframework.recipeapp.domain.Recipe;
import guru.springframework.recipeapp.repositories.RecipeRepository;
import guru.springframework.recipeapp.repositories.UnitOfMeasureRepository;

class IngredientServiceImplTest {
	
	private final IngredientToIngredientCommand ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
	private final IngredientCommandToIngredient ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());

	@Mock private RecipeRepository recipeRepository;
	@Mock private UnitOfMeasureRepository uomOfMeasureRepository;
	private IngredientService ingredientService;

	@BeforeEach
	void setUp() throws Exception {
		try(AutoCloseable ac = MockitoAnnotations.openMocks(this)) {
			ingredientService = new IngredientServiceImpl(recipeRepository, uomOfMeasureRepository, ingredientToIngredientCommand, ingredientCommandToIngredient);
		}
	}

	@Test
	void testFindByRecipeIdAndIngredientId() {
		// TODO
	}
	
	@Test
	void testFindByRecipeIdAndIngredientIdHappyPath() {
		// Given
		Recipe recipe = Recipe.builder().id("1").build();
		Set.of(
			Ingredient.builder().id("1").build(),
			Ingredient.builder().id("2").build(),
			Ingredient.builder().id("3").build()
		).forEach(recipe::addIngredient);
		when(recipeRepository.findById(Mockito.anyString())).thenReturn(Optional.of(recipe));

		// When
		IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId("1", "3");
		
		// Then
		assertEquals("3", ingredientCommand.getId());
		assertEquals("1", ingredientCommand.getRecipeId());
		verify(recipeRepository, times(1)).findById(Mockito.anyString());
	}
	
	@Test
	void saveRecipeCommand() {
		// Given
		IngredientCommand command = new IngredientCommand();
		command.setId("3");
		command.setRecipeId("2");
		
		Optional<Recipe> recipeOptional = Optional.of(new Recipe());
		
		Recipe savedRecipe = new Recipe();
		savedRecipe.addIngredient(Ingredient.builder().id("3").build());
		
		when(recipeRepository.findById(Mockito.anyString())).thenReturn(recipeOptional);
		when(recipeRepository.save(Mockito.any())).thenReturn(savedRecipe);
		
		// When
		IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);
		
		// Then
		assertEquals("3", savedCommand.getId());
		verify(recipeRepository, times(1)).findById(Mockito.anyString());
		verify(recipeRepository, times(1)).save(Mockito.any(Recipe.class));
	}

	@Test
	void deleteById() {
		// Given
		Recipe recipe = Recipe.builder().id("1").build();
		recipe.addIngredient(Ingredient.builder().id("3").build());
		when(recipeRepository.findById(Mockito.anyString())).thenReturn(Optional.of(recipe));
		
		// When
		ingredientService.deleteById("1", "3");
		// Then
		verify(recipeRepository, times(1)).findById(Mockito.anyString());
		verify(recipeRepository, times(1)).save(Mockito.any(Recipe.class));
	}
}
