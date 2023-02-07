package alex.klimchuk.recipe.dto;

import alex.klimchuk.recipe.domain.Difficulty;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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

    @NotBlank
    @Size(min = 3, max = 255)
    private String description;

    @Min(1)
    @Max(999)
    private Integer prepTime;

    @Min(1)
    @Max(999)
    private Integer cookTime;

    @Min(1)
    @Max(100)
    private Integer servings;

    private String source;

    @URL
    @NotBlank
    private String url;

    @NotBlank
    private String directions;

    private Set<IngredientDto> ingredients = new HashSet<>();

    private Byte[] image;

    @NotBlank
    private Difficulty difficulty;

    private NotesDto notes;

    private Set<CategoryDto> categories = new HashSet<>();

}
