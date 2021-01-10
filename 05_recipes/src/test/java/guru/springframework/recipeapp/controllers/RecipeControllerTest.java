package guru.springframework.recipeapp.controllers;

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

import guru.springframework.recipeapp.domain.Recipe;
import guru.springframework.recipeapp.service.RecipeService;

class RecipeControllerTest {
	
	@Mock private RecipeService recipeService;
	private RecipeController controller;

	@BeforeEach
	void setUp() throws Exception {
		try (AutoCloseable ac = MockitoAnnotations.openMocks(this)) {
			controller = new RecipeController(recipeService);
		}
	}
	
	@Test
	void getRecipe() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		
		when(recipeService.findById(Mockito.anyLong())).thenReturn(recipe);
		
		mockMvc.perform(get("/recipe/show/1"))
			.andExpect(status().isOk())
			.andExpect(view().name("recipe/show"))
			.andExpect(model().attributeExists("recipe"));
	}

}
