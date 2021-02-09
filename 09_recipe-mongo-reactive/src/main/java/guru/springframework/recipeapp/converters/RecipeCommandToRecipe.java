package guru.springframework.recipeapp.converters;

import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import guru.springframework.recipeapp.commands.RecipeCommand;
import guru.springframework.recipeapp.domain.Recipe;
import lombok.Synchronized;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {
	
	private final IngredientCommandToIngredient ingredientConverter;
	private final NotesCommandToNotes noteConverter;
	private final CategoryCommandToCategory categoryConverter;
	
	public RecipeCommandToRecipe(IngredientCommandToIngredient ingredientConverter, NotesCommandToNotes noteConverter, CategoryCommandToCategory categoryConverter) {
		this.ingredientConverter = ingredientConverter;
		this.noteConverter = noteConverter;
		this.categoryConverter = categoryConverter;
	}


	@Override
	@Synchronized
	@Nullable
	public Recipe convert(RecipeCommand source) {
		if(source == null) {
			return null;
		}
		final Recipe recipe = new Recipe();
		if(StringUtils.isNotBlank(source.getId())) {
			recipe.setId(source.getId());
		}
		recipe.setDescription(source.getDescription());
		recipe.setPrepTime(source.getPrepTime());
		recipe.setCookTime(source.getCookTime());
		recipe.setServings(source.getServings());
		recipe.setSource(source.getSource());
		recipe.setUrl(source.getUrl());
		recipe.setDirections(source.getDirections());
		recipe.setDifficulty(source.getDifficulty());
		if(source.getNotes() != null) {
			recipe.setNotes(noteConverter.convert(source.getNotes()));
		}
		if(source.getIngredients() != null && !source.getIngredients().isEmpty()) {
			source.getIngredients()
				.forEach(ingredient -> recipe.addIngredient(ingredientConverter.convert(ingredient)));
		}
		if(source.getCategories() != null && !source.getCategories().isEmpty()) {
			recipe.setCategories(source.getCategories().stream().map(categoryConverter::convert).collect(Collectors.toSet()));
		}
		return recipe;
	}

}
