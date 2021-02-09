package guru.springframework.recipeapp.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import guru.springframework.recipeapp.commands.IngredientCommand;
import guru.springframework.recipeapp.commands.RecipeCommand;
import guru.springframework.recipeapp.commands.UnitOfMeasureCommand;
import guru.springframework.recipeapp.service.IngredientService;
import guru.springframework.recipeapp.service.RecipeService;
import guru.springframework.recipeapp.service.UnitOfMeasureService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest(IngredientController.class)
@AutoConfigureWebTestClient(timeout = "36000")
class IngredientControllerTest {
	
	@MockBean private RecipeService recipeService;
	@MockBean private IngredientService ingredientService;
	@MockBean private UnitOfMeasureService uomOfMeasureService;
	@Autowired private WebTestClient webTestClient;

	@Test
	void listIngredients() throws Exception {
		// Given
		RecipeCommand recipeCommand = new RecipeCommand();
		when(recipeService.findCommandById(Mockito.anyString())).thenReturn(Mono.just(recipeCommand));
		
		// When
		webTestClient
			.get()
			.uri("/recipe/1/ingredients")
			.exchange()
			.expectStatus().isOk();
		verify(recipeService, times(1)).findCommandById(Mockito.anyString());
	}
	
	@Test
	void showIngredient() throws Exception {
		// Given
		IngredientCommand ingredientCommand = IngredientCommand.builder()
				.uom(UnitOfMeasureCommand.builder().id("1").build())
				.build();
		when(ingredientService.findByRecipeIdAndIngredientId(Mockito.anyString(), Mockito.anyString())).thenReturn(Mono.just(ingredientCommand));
		
		// When
		webTestClient
			.get()
			.uri("/recipe/1/ingredient/2/show")
			.exchange()
			.expectStatus().isOk();
		verify(ingredientService, times(1)).findByRecipeIdAndIngredientId(Mockito.anyString(), Mockito.anyString());
	}
	
	@Test
	void updateIngredientForm() throws Exception {
		// Given
		List<UnitOfMeasureCommand> uoms = List.of(
				UnitOfMeasureCommand.builder().id("1").build(),
				UnitOfMeasureCommand.builder().id("2").build(),
				UnitOfMeasureCommand.builder().id("3").build()
		);
		
		IngredientCommand ingredientCommand = IngredientCommand.builder()
				.uom(UnitOfMeasureCommand.builder().id("1").build())
				.build();

		when(ingredientService.findByRecipeIdAndIngredientId(Mockito.anyString(), Mockito.anyString())).thenReturn(Mono.just(ingredientCommand));
		when(uomOfMeasureService.listAllUoms()).thenReturn(Flux.fromIterable(uoms));
		
		// When
		webTestClient
			.get()
			.uri("/recipe/1/ingredient/2/update")
			.exchange()
			.expectStatus().isOk();
	}
	
	@Test
	void newIngredientForm() throws Exception {
		// Given
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId("1");

		when(recipeService.findCommandById(Mockito.anyString())).thenReturn(Mono.just(recipeCommand));
		when(uomOfMeasureService.listAllUoms()).thenReturn(Flux.just(UnitOfMeasureCommand.builder().id("1").build()));
		
		// When
		webTestClient
			.get()
			.uri("/recipe/1/ingredient/new")
			.exchange()
			.expectStatus().isOk();
		verify(recipeService, times(1)).findCommandById(Mockito.anyString());
	}
	
	@Test
	void saveOrUpdate() throws Exception {
		// Given
		IngredientCommand command = new IngredientCommand();
		command.setId("3");
		command.setRecipeId("2");

		when(ingredientService.saveIngredientCommand(Mockito.any(IngredientCommand.class))).thenReturn(Mono.just(command));

		webTestClient
			.post()
			.uri("/recipe/1/ingredient")
			.body(BodyInserters
					.fromFormData("recipeId", "1")
					.with("description", "some string")
					.with("amount", "3")
					.with("uom.id", "1")
			)
			.exchange()
			.expectStatus().is3xxRedirection()
			.expectHeader().location("/recipe/2/ingredient/3/show");
	}

	@Test
	void deleteIngredient() throws Exception {
		when(ingredientService.deleteById(Mockito.anyString(), Mockito.anyString())).thenReturn(Mono.empty());
		
		webTestClient
			.get()
			.uri("/recipe/2/ingredient/3/delete")
			.exchange()
			.expectStatus().is3xxRedirection()
			.expectHeader().location("/recipe/2/ingredients");
		
		verify(ingredientService, times(1)).deleteById(Mockito.anyString(), Mockito.anyString());
	}
}
