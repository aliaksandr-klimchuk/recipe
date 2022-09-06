package alex.klimchuk.recipe.services;

import alex.klimchuk.recipe.domain.Recipe;

import java.util.Set;

/**
 * Copyright Alex Klimchuk (c) 2022.
 */
public interface RecipeService {

    Set<Recipe> getRecipes();

}
