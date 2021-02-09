package guru.springframework.recipeapp.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import guru.springframework.recipeapp.commands.IngredientCommand;
import guru.springframework.recipeapp.converters.IngredientCommandToIngredient;
import guru.springframework.recipeapp.converters.IngredientToIngredientCommand;
import guru.springframework.recipeapp.domain.Ingredient;
import guru.springframework.recipeapp.domain.Recipe;
import guru.springframework.recipeapp.domain.UnitOfMeasure;
import guru.springframework.recipeapp.repositories.reactive.RecipeReactiveRepository;
import guru.springframework.recipeapp.repositories.reactive.UnitOfMeasureReactiveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

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
				.flatMapIterable(r -> r.getIngredients())
				.filter(ingredient -> ingredientId.equalsIgnoreCase(ingredient.getId()))
				.single()
				.map(ingredientToIngredientCommand::convert)
				.doOnNext(i -> i.setRecipeId(recipeId));
	}

	@Override
	public Mono<IngredientCommand> saveIngredientCommand(IngredientCommand command) {
		Mono<UnitOfMeasure> monoUom = uomOfMeasureReactiveRepository.findById(command.getUom().getId());
		Mono<Recipe> monoRecipe = recipeReactiveRepository.findById(command.getRecipeId());
		return Flux.combineLatest(monoUom, monoRecipe, (a,b) -> Tuples.of(a, b))
			.flatMap(pair -> {
				Recipe recipe = pair.getT2();
				UnitOfMeasure uom = pair.getT1();
				if(recipe == null) {
					// TODO throw error if not exists
					log.error("Recipe not found for id " + command.getRecipeId());
					return Mono.just(new IngredientCommand());
				}
			
				Optional<Ingredient> ingredientOptional = recipe
						.getIngredients()
						.stream()
						.filter(i -> i.getId().equals(command.getId()))
						.findFirst();
				if(ingredientOptional.isPresent()) {
					Ingredient ingredient = ingredientOptional.get();
					ingredient.setDescription(command.getDescription());
					ingredient.setAmount(command.getAmount());
					ingredient.setUom(uom);
				} else {
					// Add new ingredient
					Ingredient ingredient = ingredientCommandToIngredient.convert(command);
					ingredient.setUom(uom);
					recipe.addIngredient(ingredient);
				}
				
				return recipeReactiveRepository.save(recipe)
					.map(savedRecipe ->
						{return ingredientToIngredientCommand.convert(savedRecipe.getIngredients()
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
							.get()
						);}
					).doOnNext(ic -> ic.setRecipeId(command.getRecipeId()));
			})
			.next();
	}

	@Override
	public Mono<Void> deleteById(String recipeId, String ingredientId) {
		log.debug("Deleting ingredient:"+recipeId+":"+ingredientId);
		return recipeReactiveRepository.findById(recipeId)
			.flatMap(recipe -> {
				if(recipe == null) {
					log.debug("Recipe id not found. Id:" + recipeId);
					return Mono.empty();
				}
				log.debug("Found recipe");
				Optional<Ingredient> optionalIngredient = recipe.getIngredients()
						.stream()
						.filter(i -> ingredientId.equalsIgnoreCase(i.getId()))
						.findFirst();
				if(optionalIngredient.isEmpty()) {
					return Mono.empty();
				}
				log.debug("Found ingredient");
				Ingredient ingredient = optionalIngredient.get();
				recipe.getIngredients().remove(ingredient);
				return recipeReactiveRepository.save(recipe).flatMap(r -> Mono.empty());
			});
	}
	
}
