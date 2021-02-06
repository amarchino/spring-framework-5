package guru.springframework.recipeapp.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import guru.springframework.recipeapp.domain.Recipe;
import guru.springframework.recipeapp.service.RecipeService;
import reactor.core.publisher.Flux;

class IndexControllerTest {
	
	private IndexController controller;
	@Mock private RecipeService recipeService;
	@Mock private Model model;
	private MockMvc mockMvc;

	@BeforeEach
	void setUp() throws Exception {
		try (AutoCloseable ac = MockitoAnnotations.openMocks(this)) {
			controller = new IndexController(recipeService);
			mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(new ControllerExceptionHandler()).build();
		}
	}
	
	@Test
	void testMockMvc() throws Exception {
		
		Mockito.when(recipeService.getRecipes()).thenReturn(Flux.empty());
		
		mockMvc.perform(get("/"))
			.andExpect(status().isOk())
			.andExpect(view().name("index"));
	}

	@Test
	void testGetIndexPage() {
		// Given 
		Mockito.when(recipeService.getRecipes()).thenReturn(Flux.just(
			Recipe.builder().build(),
			Recipe.builder().id("1").build()
		));
		@SuppressWarnings("unchecked")
		ArgumentCaptor<List<Recipe>> argumentCaptor = ArgumentCaptor.forClass(List.class);
		
		// When
		String result = controller.getIndexPage(model);
		
		// Then
		assertEquals("index", result);
		Mockito.verify(recipeService, Mockito.times(1)).getRecipes();
		Mockito.verify(model, Mockito.times(1)).addAttribute(Mockito.eq("recipes"), argumentCaptor.capture());
		List<Recipe> setInController = argumentCaptor.getValue();
		assertEquals(2, setInController.size());
	}

}
