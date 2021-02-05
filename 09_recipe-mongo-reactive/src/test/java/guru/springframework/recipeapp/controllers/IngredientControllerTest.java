package guru.springframework.recipeapp.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.recipeapp.commands.IngredientCommand;
import guru.springframework.recipeapp.commands.RecipeCommand;
import guru.springframework.recipeapp.service.IngredientService;
import guru.springframework.recipeapp.service.RecipeService;
import guru.springframework.recipeapp.service.UnitOfMeasureService;

class IngredientControllerTest {
	
	@Mock private RecipeService recipeService;
	@Mock private IngredientService ingredientService;
	@Mock private UnitOfMeasureService uomOfMeasureService;
	private IngredientController controller;
	private MockMvc mockMvc;

	@BeforeEach
	void setUp() throws Exception {
		try(AutoCloseable ac = MockitoAnnotations.openMocks(this)) {
			controller = new IngredientController(recipeService, ingredientService, uomOfMeasureService);
			mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		}
	}

	@Test
	void listIngredients() throws Exception {
		// Given
		RecipeCommand recipeCommand = new RecipeCommand();
		when(recipeService.findCommandById(Mockito.anyString())).thenReturn(recipeCommand);
		
		// When
		mockMvc.perform(get("/recipe/1/ingredients"))
		// Then
			.andExpect(status().isOk())
			.andExpect(view().name("recipe/ingredient/list"))
			.andExpect(model().attributeExists("recipe"));
		verify(recipeService, times(1)).findCommandById(Mockito.anyString());
	}
	
	@Test
	void showIngredient() throws Exception {
		// Given
		IngredientCommand ingredientCommand = new IngredientCommand();
		when(ingredientService.findByRecipeIdAndIngredientId(Mockito.anyString(), Mockito.anyString())).thenReturn(ingredientCommand);
		
		// When
		mockMvc.perform(get("/recipe/1/ingredient/2/show"))
		// Then
			.andExpect(status().isOk())
			.andExpect(view().name("recipe/ingredient/show"))
			.andExpect(model().attributeExists("ingredient"));
		verify(ingredientService, times(1)).findByRecipeIdAndIngredientId(Mockito.anyString(), Mockito.anyString());
	}
	
	@Test
	void updateIngredientForm() throws Exception {
		// Given
		IngredientCommand ingredientCommand = new IngredientCommand();

		when(ingredientService.findByRecipeIdAndIngredientId(Mockito.anyString(), Mockito.anyString())).thenReturn(ingredientCommand);
		when(uomOfMeasureService.listAllUoms()).thenReturn(new HashSet<>());
		
		// When
		mockMvc.perform(get("/recipe/1/ingredient/2/update"))
		// Then
			.andExpect(status().isOk())
			.andExpect(view().name("recipe/ingredient/ingredient-form"))
			.andExpect(model().attributeExists("ingredient"))
			.andExpect(model().attributeExists("uomList"));
	}
	
	@Test
	void newIngredientForm() throws Exception {
		// Given
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId("1");

		when(recipeService.findCommandById(Mockito.anyString())).thenReturn(recipeCommand);
		when(uomOfMeasureService.listAllUoms()).thenReturn(new HashSet<>());
		
		// When
		mockMvc.perform(get("/recipe/1/ingredient/new"))
		// Then
			.andExpect(status().isOk())
			.andExpect(view().name("recipe/ingredient/ingredient-form"))
			.andExpect(model().attributeExists("ingredient"))
			.andExpect(model().attributeExists("uomList"));
		verify(recipeService, times(1)).findCommandById(Mockito.anyString());
	}
	
	@Test
	void saveOrUpdate() throws Exception {
		// Given
		IngredientCommand command = new IngredientCommand();
		command.setId("3");
		command.setRecipeId("2");

		when(ingredientService.saveIngredientCommand(Mockito.any(IngredientCommand.class))).thenReturn(command);
		
		// When
		mockMvc.perform(
				post("/recipe/1/ingredient")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("id", "3")
				.param("description", "some string")
			)
		// Then
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/recipe/2/ingredient/3/show"));
	}

	@Test
	void deleteIngredient() throws Exception {
		mockMvc.perform(get("/recipe/2/ingredient/3/delete"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/recipe/2/ingredients"));
		verify(ingredientService, times(1)).deleteById(Mockito.anyString(), Mockito.anyString());
	}
}
