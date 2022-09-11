package alex.klimchuk.recipe.services;

import alex.klimchuk.recipe.converters.RecipeDtoToRecipe;
import alex.klimchuk.recipe.converters.RecipeToRecipeDto;
import alex.klimchuk.recipe.domain.Recipe;
import alex.klimchuk.recipe.dto.RecipeDto;
import alex.klimchuk.recipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Copyright Alex Klimchuk (c) 2022.
 */
@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    private final RecipeDtoToRecipe recipeDtoToRecipe;

    private final RecipeToRecipeDto recipeToRecipeDto;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeDtoToRecipe recipeDtoToRecipe,
                             RecipeToRecipeDto recipeToRecipeDto) {
        this.recipeRepository = recipeRepository;
        this.recipeDtoToRecipe = recipeDtoToRecipe;
        this.recipeToRecipeDto = recipeToRecipeDto;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("Executed method get Recipes!");
        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
        return recipes;
    }

    @Override
    public Recipe findById(Long id) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(id);

        if (recipeOptional.isEmpty()) {
            throw new RuntimeException("Recipe Not Found!");
        }

        return recipeOptional.get();
    }

    @Override
    @Transactional
    public RecipeDto saveRecipeDto(RecipeDto recipeDto) {
        Recipe detachedRecipe = recipeDtoToRecipe.convert(recipeDto);

        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug("Saved RecipeId:" + savedRecipe.getId());

        return recipeToRecipeDto.convert(savedRecipe);
    }

}
