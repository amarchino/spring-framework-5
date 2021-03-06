package guru.springframework.recipeapp.service;

import org.springframework.stereotype.Service;

import guru.springframework.recipeapp.commands.RecipeCommand;
import guru.springframework.recipeapp.converters.RecipeCommandToRecipe;
import guru.springframework.recipeapp.converters.RecipeToRecipeCommand;
import guru.springframework.recipeapp.domain.Recipe;
import guru.springframework.recipeapp.exceptions.NotFoundException;
import guru.springframework.recipeapp.repositories.reactive.RecipeReactiveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecipeServiceImpl implements RecipeService {
	
	private final RecipeReactiveRepository recipeReactiveRepository;
	private final RecipeCommandToRecipe recipeCommandToRecipe;
	private final RecipeToRecipeCommand recipeToRecipeCommand;

	@Override
	public Flux<Recipe> getRecipes() {
		log.debug("I'm in the service");
		return recipeReactiveRepository
			.findAll();
	}

	@Override
	public Mono<Recipe> findById(String id) {
		return recipeReactiveRepository
				.findById(id)
				.switchIfEmpty(Mono.error(new NotFoundException("Recipe, id: " + id)));
	}

	@Override
	public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command) {
		return recipeReactiveRepository
				.save(recipeCommandToRecipe.convert(command))
				.doOnNext(savedRecipe -> log.debug("Saved RecipeId: " + savedRecipe.getId()))
				.single()
				.map(recipeToRecipeCommand::convert);
	}

	@Override
	public Mono<RecipeCommand> findCommandById(String id) {
		return findById(id)
				.map(recipeToRecipeCommand::convert)
				.doOnNext(rc -> rc.getIngredients().forEach(i -> i.setRecipeId(id)));
	}

	@Override
	public Mono<Void> deleteById(String id) {
		return recipeReactiveRepository.deleteById(id);
	}
	
}
