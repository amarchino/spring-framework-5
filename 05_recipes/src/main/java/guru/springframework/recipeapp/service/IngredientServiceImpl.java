package guru.springframework.recipeapp.service;

import org.springframework.stereotype.Service;

import guru.springframework.recipeapp.commands.IngredientCommand;
import guru.springframework.recipeapp.converters.IngredientToIngredientCommand;
import guru.springframework.recipeapp.repositories.RecipeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {
	
	private final RecipeRepository recipeRepository;
	private final IngredientToIngredientCommand ingredientToIngredientCommand;

	@Override
	public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
		return recipeRepository.findById(recipeId)
			.orElseThrow(() -> new RuntimeException("Recipe not found"))
			.getIngredients()
			.stream()
			.filter(ingredient -> ingredientId.equals(ingredient.getId()))
			.map(ingredientToIngredientCommand::convert)
			.findFirst()
			.orElseThrow(() -> new RuntimeException("Ingredient not found"));
	}
	
}
