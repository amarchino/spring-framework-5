package guru.springframework.recipeapp.service;

import guru.springframework.recipeapp.commands.RecipeCommand;
import guru.springframework.recipeapp.domain.Recipe;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RecipeService {
	
	Flux<Recipe> getRecipes();
	Mono<Recipe> findById(String id);
	Mono<RecipeCommand> findCommandById(String id);
	Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command);
	Mono<Void> deleteById(String id);

}
