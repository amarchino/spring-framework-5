package guru.springframework.recipeapp.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import guru.springframework.recipeapp.commands.IngredientCommand;
import guru.springframework.recipeapp.converters.IngredientCommandToIngredient;
import guru.springframework.recipeapp.converters.IngredientToIngredientCommand;
import guru.springframework.recipeapp.domain.Ingredient;
import guru.springframework.recipeapp.domain.Recipe;
import guru.springframework.recipeapp.repositories.reactive.RecipeReactiveRepository;
import guru.springframework.recipeapp.repositories.reactive.UnitOfMeasureReactiveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class IngredientServiceImpl implements IngredientService {
	
	private final RecipeReactiveRepository recipeReactiveRepository;
	private final UnitOfMeasureReactiveRepository uomOfMeasureReactiveRepository;
	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final IngredientCommandToIngredient ingredientCommandToIngredient;

	@Override
	public Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId) {
		return recipeReactiveRepository
				.findById(recipeId)
				.flatMapMany(r -> Flux.fromIterable(r.getIngredients()))
				.filter(ingredient -> ingredientId.equals(ingredient.getId()))
				.map(ingredientToIngredientCommand::convert)
				.doOnNext(i -> i.setRecipeId(recipeId))
				.last();
	}

	@Override
	public Mono<IngredientCommand> saveIngredientCommand(IngredientCommand command) {
		Optional<Recipe> recipeOptional = recipeReactiveRepository.findById(command.getRecipeId()).blockOptional();
		if(!recipeOptional.isPresent()) {
			// TODO throw error if not exists
			log.error("Recipe not found for id " + command.getRecipeId());
			return Mono.just(new IngredientCommand());
		}
		Recipe recipe = recipeOptional.get();
		Optional<Ingredient> ingredientOptional = recipe
				.getIngredients()
				.stream()
				.filter(i -> i.getId().equals(command.getId()))
				.findFirst();
		if(ingredientOptional.isPresent()) {
			Ingredient ingredient = ingredientOptional.get();
			ingredient.setDescription(command.getDescription());
			ingredient.setAmount(command.getAmount());
			ingredient.setUom(uomOfMeasureReactiveRepository.findById(command.getUom().getId()).blockOptional().orElseThrow(() -> new RuntimeException("Unit not found")));
		} else {
			// Add new ingredient
			recipe.addIngredient(ingredientCommandToIngredient.convert(command));
		}
		Recipe savedRecipe = recipeReactiveRepository.save(recipe).block();
		return Mono.just(ingredientToIngredientCommand.convert(savedRecipe.getIngredients()
				.stream()
				.filter(i -> i.getId().equals(command.getId()))
				.findFirst()
				.or(() -> savedRecipe
					.getIngredients()
					.stream()
					.filter(ri -> ri.getDescription().equals(command.getDescription()))
					.filter(ri -> ri.getAmount().compareTo(command.getAmount()) == 0)
					.filter(ri -> ri.getUom().getId().equals(command.getUom().getId()))
					.findFirst()
				)
				.get())
		);
		
	}

	@Override
	public Mono<Void> deleteById(String recipeId, String ingredientId) {
		log.debug("Deleting ingredient:"+recipeId+":"+ingredientId);
		Optional<Recipe> optionalRecipe = recipeReactiveRepository.findById(recipeId).blockOptional();
		if(!optionalRecipe.isPresent()) {
			log.debug("Recipe id not found. Id:" + recipeId);
			return Mono.empty();
		}
		Recipe recipe = optionalRecipe.get();
		
		log.debug("Found recipe");
		Optional<Ingredient> optionalIngredient = recipe.getIngredients()
			.stream()
			.filter(i -> ingredientId.equals(i.getId()))
			.findFirst();
		if(optionalIngredient.isPresent()) {
			log.debug("Found ingredient");
			Ingredient ingredient = optionalIngredient.get();
			recipe.getIngredients().remove(ingredient);
			recipeReactiveRepository.save(recipe).block();
		}
		return Mono.empty();
	}
	
}