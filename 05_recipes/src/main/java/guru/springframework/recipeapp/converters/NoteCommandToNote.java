package guru.springframework.recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import guru.springframework.recipeapp.commands.NoteCommand;
import guru.springframework.recipeapp.domain.Note;
import lombok.Synchronized;

@Component
public class NoteCommandToNote implements Converter<NoteCommand, Note> {

	@Override
	@Synchronized
	@Nullable
	public Note convert(NoteCommand source) {
		if(source == null) {
			return null;
		}
		final Note note = new Note();
		note.setId(source.getId());
		note.setRecipeNote(source.getRecipeNote());
		return note;
	}

}
