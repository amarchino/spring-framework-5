package guru.springframework.recipeapp.controllers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import guru.springframework.recipeapp.domain.Recipe;
import guru.springframework.recipeapp.service.RecipeService;
import reactor.core.publisher.Flux;

@ExtendWith(SpringExtension.class)
@WebFluxTest(IndexController.class)
class IndexControllerTest {
	
	@MockBean private RecipeService recipeService;
	@Autowired private WebTestClient webTestClient;
	
	@Test
	void testMockMvc() throws Exception {
		
		Mockito.when(recipeService.getRecipes()).thenReturn(Flux.just(
			Recipe.builder().description("TEST").build()
		));
		
		EntityExchangeResult<String> response = webTestClient
			.get()
			.uri("/")
			.exchange()
			.expectStatus().isOk()
			.expectBody(String.class)
			.returnResult();
		assertNotNull(response.getResponseBody());
		assertTrue(response.getResponseBody().contains("TEST"));
		
		Mockito.verify(recipeService, Mockito.times(1)).getRecipes();
	}

}
