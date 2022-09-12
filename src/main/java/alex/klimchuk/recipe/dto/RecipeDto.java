package alex.klimchuk.recipe.dto;

import alex.klimchuk.recipe.domain.Difficulty;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Copyright Alex Klimchuk (c) 2022.
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDto {

    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    private Set<IngredientDto> ingredients = new HashSet<>();
    private Byte[] image;
    private Difficulty difficulty;
    private NotesDto notes;
    private Set<CategoryDto> categories = new HashSet<>();

}
