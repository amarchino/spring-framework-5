package guru.springframework.recipeapp.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.recipeapp.commands.RecipeCommand;
import guru.springframework.recipeapp.domain.Recipe;
import guru.springframework.recipeapp.exceptions.NotFoundException;
import guru.springframework.recipeapp.service.RecipeService;

class RecipeControllerTest {

	@Mock
	private RecipeService recipeService;
	private RecipeController controller;
	private MockMvc mockMvc;

	@BeforeEach
	void setUp() throws Exception {
		try (AutoCloseable ac = MockitoAnnotations.openMocks(this)) {
			controller = new RecipeController(recipeService);
			mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(new ControllerExceptionHandler()).build();
		}
	}

	@Test
	void getRecipe() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		when(recipeService.findById(Mockito.anyLong())).thenReturn(recipe);

		mockMvc.perform(get("/recipe/1/show")).andExpect(status().isOk()).andExpect(view().name("recipe/show"))
				.andExpect(model().attributeExists("recipe"));
	}
	
	@Test
	void getRecipeNotFound() throws Exception {
		when(recipeService.findById(Mockito.anyLong())).thenThrow(NotFoundException.class);

		mockMvc.perform(get("/recipe/1/show"))
			.andExpect(status().isNotFound())
			.andExpect(view().name("404error"));
	}

	@Test
	void getRecipeNumberFormat() throws Exception {
		mockMvc.perform(get("/recipe/aaa/show"))
			.andExpect(status().isBadRequest())
			.andExpect(view().name("400error"));
	}

	@Test
	public void getNewRecipeForm() throws Exception {
		mockMvc.perform(get("/recipe/new"))
			.andExpect(status().isOk())
			.andExpect(view().name("recipe/recipe-form"))
			.andExpect(model().attributeExists("recipe"));
	}

	@Test
	public void postNewRecipeForm() throws Exception {
		RecipeCommand command = new RecipeCommand();
		command.setId(2L);

		when(recipeService.saveRecipeCommand(Mockito.any())).thenReturn(command);

		mockMvc.perform(
				post("/recipe").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("id", "")
				.param("description", "some string")
				.param("directions", "some directions")
			)
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/recipe/2/show"));
	}
	
	@Test
	public void postNewRecipeFormValidationFail() throws Exception {
		RecipeCommand command = new RecipeCommand();
		command.setId(2L);

		when(recipeService.saveRecipeCommand(Mockito.any())).thenReturn(command);

		mockMvc.perform(
				post("/recipe").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("id", "")
			)
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("recipe"))
			.andExpect(view().name("recipe/recipe-form"));
	}

	@Test
	public void getUpdateView() throws Exception {
		RecipeCommand command = new RecipeCommand();
		command.setId(2L);

		when(recipeService.findCommandById(Mockito.anyLong())).thenReturn(command);

		mockMvc.perform(get("/recipe/1/update"))
			.andExpect(status().isOk())
			.andExpect(view().name("recipe/recipe-form"))
			.andExpect(model().attributeExists("recipe"));
	}
	
	@Test
	public void deleteRecipe() throws Exception {
		mockMvc.perform(get("/recipe/1/delete"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/"));
		verify(recipeService, times(1)).deleteById(Mockito.anyLong());
	}

}
