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
public class IngredientDto {

    private Long id;
    private Long recipeId;
    private String description;
    private Double amount;
    private UnitOfMeasureDto unitOfMeasure;

}
