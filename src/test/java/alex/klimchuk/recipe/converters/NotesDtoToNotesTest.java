package alex.klimchuk.recipe.converters;

import alex.klimchuk.recipe.dto.NotesDto;
import alex.klimchuk.recipe.domain.Notes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Copyright Alex Klimchuk (c) 2022.
 */
public class NotesDtoToNotesTest {

    public static final Long ID_VALUE = 1L;
    public static final String RECIPE_NOTES = "Notes";
    NotesDtoToNotes converter;

    @Before
    public void setUp() throws Exception {
        converter = new NotesDtoToNotes();
    }

    @Test
    public void testNullParameter() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new NotesDto()));
    }

    @Test
    public void testConvert() {
        NotesDto notesDto = NotesDto.builder()
                .id(ID_VALUE)
                .recipeNotes(RECIPE_NOTES)
                .build();

        Notes notes = converter.convert(notesDto);

        assertNotNull(notes);
        assertEquals(ID_VALUE, notes.getId());
        assertEquals(RECIPE_NOTES, notes.getRecipeNotes());
    }

}
