package guru.springframework.recipeapp.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guru.springframework.recipeapp.commands.NoteCommand;
import guru.springframework.recipeapp.domain.Note;

public class NoteToNoteCommandTest {
	private static final Long ID_VALUE = 1L;
	private static final String RECIPE_NOTES = "Notes";
	private NoteToNoteCommand converter;

	@BeforeEach
	public void setUp() throws Exception {
		converter = new NoteToNoteCommand();
	}

	@Test
	public void convert() throws Exception {
		// given
		Note notes = new Note();
		notes.setId(ID_VALUE);
		notes.setRecipeNote(RECIPE_NOTES);

		// when
		NoteCommand notesCommand = converter.convert(notes);

		// then
		assertEquals(ID_VALUE, notesCommand.getId());
		assertEquals(RECIPE_NOTES, notesCommand.getRecipeNote());
	}

	@Test
	public void testNull() throws Exception {
		assertNull(converter.convert(null));
	}

	@Test
	public void testEmptyObject() throws Exception {
		assertNotNull(converter.convert(new Note()));
	}
}
