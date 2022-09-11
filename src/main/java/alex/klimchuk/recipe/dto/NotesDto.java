package alex.klimchuk.recipe.dto;

import lombok.*;

/**
 * Copyright Alex Klimchuk (c) 2022.
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotesDto {

    private Long id;
    private String recipeNotes;

}
