package alex.klimchuk.recipe.converters;

import alex.klimchuk.recipe.domain.Ingredient;
import alex.klimchuk.recipe.domain.Recipe;
import alex.klimchuk.recipe.dto.IngredientDto;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Copyright Alex Klimchuk (c) 2022.
 */
@Component
public class IngredientDtoToIngredient implements Converter<IngredientDto, Ingredient> {

    private final UnitOfMeasureDtoToUnitOfMeasure unitOfMeasureConverter;

    public IngredientDtoToIngredient(UnitOfMeasureDtoToUnitOfMeasure unitOfMeasureConverter) {
        this.unitOfMeasureConverter = unitOfMeasureConverter;
    }

    @Override
    @Nullable
    @Synchronized
    public Ingredient convert(IngredientDto ingredientDto) {
        Ingredient ingredient = Ingredient.builder()
                .id(ingredientDto.getId())
                .amount(ingredientDto.getAmount())
                .description(ingredientDto.getDescription())
                .unitOfMeasure(unitOfMeasureConverter.convert(ingredientDto.getUnitOfMeasure()))
                .build();

        if (Objects.nonNull(ingredientDto.getRecipeId())) {
            Recipe recipe = new Recipe();
            recipe.setId(ingredientDto.getRecipeId());
            ingredient.setRecipe(recipe);
            recipe.addIngredient(ingredient);
        }

        return ingredient;
    }

}
