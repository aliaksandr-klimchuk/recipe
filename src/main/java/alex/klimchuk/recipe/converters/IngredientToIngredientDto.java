package alex.klimchuk.recipe.converters;

import alex.klimchuk.recipe.dto.IngredientDto;
import alex.klimchuk.recipe.domain.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Copyright Alex Klimchuk (c) 2022.
 */
@Component
public class IngredientToIngredientDto implements Converter<Ingredient, IngredientDto> {

    private final UnitOfMeasureToUnitOfMeasureDto unitOfMeasureConverter;

    public IngredientToIngredientDto(UnitOfMeasureToUnitOfMeasureDto unitOfMeasureConverter) {
        this.unitOfMeasureConverter = unitOfMeasureConverter;
    }

    @Override
    @Nullable
    @Synchronized
    public IngredientDto convert(Ingredient ingredient) {
        IngredientDto ingredientDto = IngredientDto.builder()
                .id(ingredient.getId())
                .amount(ingredient.getAmount())
                .description(ingredient.getDescription())
                .unitOfMeasure(unitOfMeasureConverter.convert(ingredient.getUnitOfMeasure()))
                .build();

        if (Objects.nonNull(ingredient.getRecipe())) {
            ingredientDto.setRecipeId(ingredient.getRecipe().getId());
        }

        return ingredientDto;
    }

}
