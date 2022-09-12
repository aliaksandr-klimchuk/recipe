package alex.klimchuk.recipe.services;

import alex.klimchuk.recipe.dto.IngredientDto;

/**
 * Copyright Alex Klimchuk (c) 2022.
 */
public interface IngredientService {

    IngredientDto findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

    IngredientDto saveIngredientDto(IngredientDto ingredientDto);

    void deleteById(Long recipeId, Long id);

}
