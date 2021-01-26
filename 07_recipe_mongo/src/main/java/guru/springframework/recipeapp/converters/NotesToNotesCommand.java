package guru.springframework.recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import guru.springframework.recipeapp.commands.NotesCommand;
import guru.springframework.recipeapp.domain.Notes;
import lombok.Synchronized;

@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand> {

	@Override
	@Synchronized
	@Nullable
	public NotesCommand convert(Notes source) {
		if(source == null) {
			return null;
		}
		final NotesCommand noteCommand = new NotesCommand();
		noteCommand.setId(source.getId());
		noteCommand.setRecipeNotes(source.getRecipeNotes());
		return noteCommand;
	}

}
