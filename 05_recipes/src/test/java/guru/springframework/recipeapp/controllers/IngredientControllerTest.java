package guru.springframework.recipeapp.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.recipeapp.commands.RecipeCommand;
import guru.springframework.recipeapp.service.RecipeService;

class IngredientControllerTest {
	
	@Mock private RecipeService recipeService;
	private IngredientController controller;
	private MockMvc mockMvc;

	@BeforeEach
	void setUp() throws Exception {
		try(AutoCloseable ac = MockitoAnnotations.openMocks(this)) {
			controller = new IngredientController(recipeService);
			mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		}
	}

	@Test
	void test() throws Exception {
		// Given
		RecipeCommand recipeCommand = new RecipeCommand();
		when(recipeService.findCommandById(Mockito.anyLong())).thenReturn(recipeCommand);
		
		// When
		mockMvc.perform(get("/recipe/1/ingredients"))
		// Then
			.andExpect(status().isOk())
			.andExpect(view().name("recipe/ingredient/list"))
			.andExpect(model().attributeExists("recipe"));
		verify(recipeService, times(1)).findCommandById(Mockito.anyLong());
	}

}
