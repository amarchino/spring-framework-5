package guru.springframework.recipeapp.service;

import java.util.Set;

import guru.springframework.recipeapp.commands.RecipeCommand;
import guru.springframework.recipeapp.domain.Recipe;

public interface RecipeService {
	
	Set<Recipe> getRecipes();
	Recipe findById(Long id);
	RecipeCommand findCommandById(Long id);
	RecipeCommand saveRecipeCommand(RecipeCommand command);

}
