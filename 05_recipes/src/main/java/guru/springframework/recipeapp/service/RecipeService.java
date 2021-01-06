package guru.springframework.recipeapp.service;

import java.util.Set;

import guru.springframework.recipeapp.domain.Recipe;

public interface RecipeService {
	
	Set<Recipe> getRecipes();

}
