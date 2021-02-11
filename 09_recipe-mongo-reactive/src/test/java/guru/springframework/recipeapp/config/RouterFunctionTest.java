package guru.springframework.recipeapp.config;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;

import guru.springframework.recipeapp.domain.Recipe;
import guru.springframework.recipeapp.service.RecipeService;
import reactor.core.publisher.Flux;

@ExtendWith(MockitoExtension.class)
public class RouterFunctionTest {
	
	private WebTestClient webTestClient;
	@Mock private RecipeService recipeService;

	@BeforeEach
	void setUp() throws Exception {
		WebConfig webConfig = new WebConfig();
		RouterFunction<?> routerFunction = webConfig.routes(recipeService);
		webTestClient = WebTestClient.bindToRouterFunction(routerFunction).build();
	}
	
	@Test
	public void getRecipes() {
		when(recipeService.getRecipes()).thenReturn(Flux.just());
		
		webTestClient.get()
			.uri("/api/recipe")
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus().isOk();
	}
	
	@Test
	public void getRecipesWithData() {
		when(recipeService.getRecipes()).thenReturn(Flux.just(
				Recipe.builder().description("test").build()
		));
		
		webTestClient.get()
			.uri("/api/recipe")
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus().isOk()
			.expectBodyList(Recipe.class);
	}

}
