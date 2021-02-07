package guru.springframework.recipeapp.controllers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import guru.springframework.recipeapp.commands.NotesCommand;
import guru.springframework.recipeapp.commands.RecipeCommand;
import guru.springframework.recipeapp.domain.Notes;
import guru.springframework.recipeapp.domain.Recipe;
import guru.springframework.recipeapp.exceptions.NotFoundException;
import guru.springframework.recipeapp.service.RecipeService;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest(RecipeController.class)
class RecipeControllerTest {

	@MockBean private RecipeService recipeService;
	@Autowired private WebTestClient webTestClient;

	@Test
	void getRecipe() throws Exception {
		String description = "TEST";
		String recipeNotesDescription = "NOTES__DESCRIPTION";
		
		when(recipeService.findById(Mockito.anyString())).thenReturn(Mono.just(
			Recipe.builder()
				.id("1")
				.description(description)
				.notes(Notes.builder().recipeNotes(recipeNotesDescription).build())
				.build()
		));
		
		EntityExchangeResult<String> response = webTestClient
				.get()
				.uri("/recipe/1/show")
				.exchange()
				.expectStatus().isOk()
				.expectBody(String.class)
				.returnResult();
			assertNotNull(response.getResponseBody());
			assertTrue(response.getResponseBody().contains(description));
			assertTrue(response.getResponseBody().contains(recipeNotesDescription));
			
		Mockito.verify(recipeService, Mockito.times(1)).findById(Mockito.anyString());
	}
	
	@Test
	void getRecipeNotFound() throws Exception {
		when(recipeService.findById(Mockito.anyString())).thenThrow(NotFoundException.class);
		
		webTestClient
			.get()
			.uri("/recipe/1/show")
			.exchange()
			.expectStatus().isNotFound();

		Mockito.verify(recipeService, Mockito.times(1)).findById(Mockito.anyString());
	}

	@Test
	public void getNewRecipeForm() throws Exception {
		webTestClient
			.get()
			.uri("/recipe/new")
			.exchange()
			.expectStatus().isOk();
	}

	@Test
	@Disabled
	public void postNewRecipeForm() throws Exception {
//		RecipeCommand command = new RecipeCommand();
//		command.setId("2");
//
//		when(recipeService.saveRecipeCommand(Mockito.any())).thenReturn(Mono.just(command));
//
//		mockMvc.perform(
//				post("/recipe").contentType(MediaType.APPLICATION_FORM_URLENCODED)
//				.param("id", "")
//				.param("description", "some string")
//				.param("directions", "some directions")
//			)
//			.andExpect(status().is3xxRedirection())
//			.andExpect(view().name("redirect:/recipe/2/show"));
	}
	
	@Test
	@Disabled
	public void postNewRecipeFormValidationFail() throws Exception {
//		RecipeCommand command = new RecipeCommand();
//		command.setId("2");
//
//		when(recipeService.saveRecipeCommand(Mockito.any())).thenReturn(Mono.just(command));
//
//		mockMvc.perform(
//				post("/recipe").contentType(MediaType.APPLICATION_FORM_URLENCODED)
//				.param("id", "")
//				.param("cookTime", "3000")
//			)
//			.andExpect(status().isOk())
//			.andExpect(model().attributeExists("recipe"))
//			.andExpect(view().name("recipe/recipe-form"));
	}

	@Test
	public void getUpdateView() throws Exception {
		String description = "TEST";
		String recipeNotesDescription = "NOTES__DESCRIPTION";
		
		when(recipeService.findCommandById(Mockito.anyString())).thenReturn(Mono.just(
			RecipeCommand.builder()
				.id("1")
				.description(description)
				.notes(NotesCommand.builder().recipeNotes(recipeNotesDescription).build())
				.build()
		));
		
		EntityExchangeResult<String> response = webTestClient
				.get()
				.uri("/recipe/1/update")
				.exchange()
				.expectStatus().isOk()
				.expectBody(String.class)
				.returnResult();
			assertNotNull(response.getResponseBody());
			assertTrue(response.getResponseBody().contains(description));
			assertTrue(response.getResponseBody().contains(recipeNotesDescription));
			
		Mockito.verify(recipeService, Mockito.times(1)).findCommandById(Mockito.anyString());
	}
	
	@Test
	@Disabled
	public void deleteRecipe() throws Exception {
//		when(recipeService.deleteById(Mockito.anyString())).thenReturn(Mono.empty());
//		
//		mockMvc.perform(get("/recipe/1/delete"))
//			.andExpect(status().is3xxRedirection())
//			.andExpect(view().name("redirect:/"));
//		verify(recipeService, times(1)).deleteById(Mockito.anyString());
	}

}
