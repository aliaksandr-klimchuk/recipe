package alex.klimchuk.recipe.dto;

import lombok.*;

import java.math.BigDecimal;

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
    private BigDecimal amount;
    private UnitOfMeasureDto unitOfMeasure;

}
