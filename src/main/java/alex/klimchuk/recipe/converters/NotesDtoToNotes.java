package alex.klimchuk.recipe.converters;

import alex.klimchuk.recipe.dto.NotesDto;
import alex.klimchuk.recipe.domain.Notes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Copyright Alex Klimchuk (c) 2022.
 */
@Component
public class NotesDtoToNotes implements Converter<NotesDto, Notes> {

    @Override
    @Nullable
    @Synchronized
    public Notes convert(NotesDto notesDto) {
        return Notes.builder()
                .id(notesDto.getId())
                .recipeNotes(notesDto.getRecipeNotes())
                .build();
    }

}
