package guru.springframework.recipeapp.converters;

import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import guru.springframework.recipeapp.commands.RecipeCommand;
import guru.springframework.recipeapp.domain.Recipe;
import lombok.Synchronized;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {
	
	private final IngredientToIngredientCommand ingredientConverter;
	private final NoteToNoteCommand noteConverter;
	private final CategoryToCategoryCommand categoryConverter;
	
	public RecipeToRecipeCommand(IngredientToIngredientCommand ingredientConverter, NoteToNoteCommand noteConverter, CategoryToCategoryCommand categoryConverter) {
		this.ingredientConverter = ingredientConverter;
		this.noteConverter = noteConverter;
		this.categoryConverter = categoryConverter;
	}


	@Override
	@Synchronized
	@Nullable
	public RecipeCommand convert(Recipe source) {
		if(source == null) {
			return null;
		}
		final RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(source.getId());
		recipeCommand.setDescription(source.getDescription());
		recipeCommand.setPrepTime(source.getPrepTime());
		recipeCommand.setCookTime(source.getCookTime());
		recipeCommand.setServings(source.getServings());
		recipeCommand.setSource(source.getSource());
		recipeCommand.setUrl(source.getUrl());
		recipeCommand.setDirections(source.getDirections());
		recipeCommand.setDifficulty(source.getDifficulty());
		recipeCommand.setNote(noteConverter.convert(source.getNote()));
		if(source.getIngredients() != null && !source.getIngredients().isEmpty()) {
			recipeCommand.setIngredients(source.getIngredients().stream().map(ingredientConverter::convert).collect(Collectors.toSet()));
		}
		if(source.getCategories() != null && !source.getCategories().isEmpty()) {
			recipeCommand.setCategories(source.getCategories().stream().map(categoryConverter::convert).collect(Collectors.toSet()));
		}
		return recipeCommand;
	}

}
