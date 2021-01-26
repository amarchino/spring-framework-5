package guru.springframework.recipeapp.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import guru.springframework.recipeapp.commands.IngredientCommand;
import guru.springframework.recipeapp.converters.IngredientCommandToIngredient;
import guru.springframework.recipeapp.converters.IngredientToIngredientCommand;
import guru.springframework.recipeapp.domain.Ingredient;
import guru.springframework.recipeapp.domain.Recipe;
import guru.springframework.recipeapp.repositories.RecipeRepository;
import guru.springframework.recipeapp.repositories.UnitOfMeasureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class IngredientServiceImpl implements IngredientService {
	
	private final RecipeRepository recipeRepository;
	private final UnitOfMeasureRepository uomOfMeasureRepository;
	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final IngredientCommandToIngredient ingredientCommandToIngredient;

	@Override
	public IngredientCommand findByRecipeIdAndIngredientId(String recipeId, String ingredientId) {
		return recipeRepository.findById(recipeId)
			.orElseThrow(() -> new RuntimeException("Recipe not found"))
			.getIngredients()
			.stream()
			.filter(ingredient -> ingredientId.equals(ingredient.getId()))
			.map(ingredientToIngredientCommand::convert)
			.findFirst()
			.orElseThrow(() -> new RuntimeException("Ingredient not found"));
	}

	@Override
	public IngredientCommand saveIngredientCommand(IngredientCommand command) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());
		if(!recipeOptional.isPresent()) {
			// TODO throw error if not exists
			log.error("Recipe not found for id " + command.getRecipeId());
			return new IngredientCommand();
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
			ingredient.setUom(uomOfMeasureRepository.findById(command.getUom().getId()).orElseThrow(() -> new RuntimeException("Unit not found")));
		} else {
			// Add new ingredient
			recipe.addIngredient(ingredientCommandToIngredient.convert(command));
		}
		Recipe savedRecipe = recipeRepository.save(recipe);
		return ingredientToIngredientCommand.convert(savedRecipe.getIngredients()
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
				.get());
		
	}

	@Override
	public void deleteById(String recipeId, String ingredientId) {
		log.debug("Deleting ingredient:"+recipeId+":"+ingredientId);
		Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
		if(!optionalRecipe.isPresent()) {
			log.debug("Recipe id not found. Id:" + recipeId);
			return;
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
			recipeRepository.save(recipe);
		}
	}
	
}
