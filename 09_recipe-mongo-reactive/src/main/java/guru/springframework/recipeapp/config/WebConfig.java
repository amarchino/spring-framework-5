package guru.springframework.recipeapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import guru.springframework.recipeapp.domain.Recipe;
import guru.springframework.recipeapp.service.RecipeService;

@Configuration
public class WebConfig {

	@Bean
	RouterFunction<?> routes(RecipeService recipeService) {
		return RouterFunctions.route(RequestPredicates.GET("/api/recipe"),
				serverRequest -> ServerResponse
					.ok()
					.contentType(MediaType.APPLICATION_JSON)
					.body(recipeService.getRecipes(), Recipe.class));
	}
}
