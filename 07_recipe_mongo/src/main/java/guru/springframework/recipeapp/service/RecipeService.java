package guru.springframework.recipeapp.service;

import java.util.Set;

import guru.springframework.recipeapp.commands.RecipeCommand;
import guru.springframework.recipeapp.domain.Recipe;

public interface RecipeService {
	
	Set<Recipe> getRecipes();
	Recipe findById(String id);
	RecipeCommand findCommandById(String id);
	RecipeCommand saveRecipeCommand(RecipeCommand command);
	void deleteById(String id);

}
