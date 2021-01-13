package guru.springframework.recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import guru.springframework.recipeapp.commands.UnitOfMeasureCommand;
import guru.springframework.recipeapp.domain.UnitOfMeasure;
import lombok.Synchronized;

@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {

	@Override
	@Synchronized
	@Nullable
	public UnitOfMeasureCommand convert(UnitOfMeasure source) {
		if(source == null) {
			return null;
		}
		final UnitOfMeasureCommand uomOfMeasureCommand = new UnitOfMeasureCommand();
		uomOfMeasureCommand.setId(source.getId());
		uomOfMeasureCommand.setDescription(source.getDescription());
		return uomOfMeasureCommand;
	}

}
