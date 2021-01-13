package guru.springframework.recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import guru.springframework.recipeapp.commands.NotesCommand;
import guru.springframework.recipeapp.domain.Notes;
import lombok.Synchronized;

@Component
public class NotesCommandToNotes implements Converter<NotesCommand, Notes> {

	@Override
	@Synchronized
	@Nullable
	public Notes convert(NotesCommand source) {
		if(source == null) {
			return null;
		}
		final Notes note = new Notes();
		note.setId(source.getId());
		note.setRecipeNotes(source.getRecipeNotes());
		return note;
	}

}
