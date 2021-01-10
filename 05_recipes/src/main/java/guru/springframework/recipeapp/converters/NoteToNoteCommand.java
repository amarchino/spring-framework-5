package guru.springframework.recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import guru.springframework.recipeapp.commands.NoteCommand;
import guru.springframework.recipeapp.domain.Note;
import lombok.Synchronized;

@Component
public class NoteToNoteCommand implements Converter<Note, NoteCommand> {

	@Override
	@Synchronized
	@Nullable
	public NoteCommand convert(Note source) {
		if(source == null) {
			return null;
		}
		final NoteCommand noteCommand = new NoteCommand();
		noteCommand.setId(source.getId());
		noteCommand.setRecipeNote(source.getRecipeNote());
		return noteCommand;
	}

}
