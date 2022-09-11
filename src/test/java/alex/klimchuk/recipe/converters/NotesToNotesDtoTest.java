package alex.klimchuk.recipe.converters;

import alex.klimchuk.recipe.dto.NotesDto;
import alex.klimchuk.recipe.domain.Notes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Copyright Alex Klimchuk (c) 2022.
 */
public class NotesToNotesDtoTest {

    public static final Long ID_VALUE = 1L;
    public static final String RECIPE_NOTES = "Notes";
    NotesToNotesDto converter;

    @Before
    public void setUp() throws Exception {
        converter = new NotesToNotesDto();
    }

    @Test
    public void testNull() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new Notes()));
    }

    @Test
    public void convert() throws Exception {
        Notes notes = Notes.builder()
                .id(ID_VALUE)
                .recipeNotes(RECIPE_NOTES)
                .build();

        NotesDto notesDto = converter.convert(notes);

        assertEquals(ID_VALUE, notesDto.getId());
        assertEquals(RECIPE_NOTES, notesDto.getRecipeNotes());
    }

}
