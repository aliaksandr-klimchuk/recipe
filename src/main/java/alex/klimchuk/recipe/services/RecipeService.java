package alex.klimchuk.recipe.services;

import alex.klimchuk.recipe.domain.Recipe;
import alex.klimchuk.recipe.dto.RecipeDto;

import java.util.Set;

/**
 * Copyright Alex Klimchuk (c) 2022.
 */
public interface RecipeService {

    Set<Recipe> getRecipes();

    Recipe findById(Long id);

    RecipeDto findDtoById(Long id);

    RecipeDto saveRecipeDto(RecipeDto recipeDto);

    void deleteById(Long id);

}
