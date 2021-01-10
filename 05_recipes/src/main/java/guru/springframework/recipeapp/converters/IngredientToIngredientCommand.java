package guru.springframework.recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import guru.springframework.recipeapp.commands.IngredientCommand;
import guru.springframework.recipeapp.domain.Ingredient;
import lombok.Synchronized;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {
	
	private final UnitOfMeasureToUnitOfMeasureCommand uomConverter;

	public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand uomConverter) {
		this.uomConverter = uomConverter;
	}

	@Override
	@Synchronized
	@Nullable
	public IngredientCommand convert(Ingredient source) {
		if(source == null) {
			return null;
		}
		final IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setId(source.getId());
		ingredientCommand.setDescription(source.getDescription());
		ingredientCommand.setAmount(source.getQuantity());
		ingredientCommand.setUnitOfMeasure(uomConverter.convert(source.getUnit()));
		return ingredientCommand;
	}

}
