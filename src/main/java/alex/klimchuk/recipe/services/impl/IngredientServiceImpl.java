package alex.klimchuk.recipe.services.impl;

import alex.klimchuk.recipe.dto.IngredientDto;
import alex.klimchuk.recipe.converters.IngredientDtoToIngredient;
import alex.klimchuk.recipe.converters.IngredientToIngredientDto;
import alex.klimchuk.recipe.domain.Ingredient;
import alex.klimchuk.recipe.domain.Recipe;
import alex.klimchuk.recipe.repositories.RecipeRepository;
import alex.klimchuk.recipe.repositories.UnitOfMeasureRepository;
import alex.klimchuk.recipe.services.IngredientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Copyright Alex Klimchuk (c) 2022.
 */
@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientDto ingredientToIngredientDto;
    private final IngredientDtoToIngredient ingredientDtoToIngredient;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(IngredientToIngredientDto ingredientToIngredientDto,
                                 IngredientDtoToIngredient ingredientDtoToIngredient,
                                 RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.ingredientToIngredientDto = ingredientToIngredientDto;
        this.ingredientDtoToIngredient = ingredientDtoToIngredient;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public IngredientDto findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (recipeOptional.isEmpty()) {
            new RuntimeException("Recipe Id Not found!");
            log.error("Recipe id not found. Id: " + recipeId);
        }

        Recipe recipe = recipeOptional.get();

        Optional<IngredientDto> ingredientDtoOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredientToIngredientDto::convert).findFirst();

        if (ingredientDtoOptional.isEmpty()) {
            new RuntimeException("Ingredient Id Not found!");
            log.error("Ingredient id not found: " + ingredientId);
        }

        return ingredientDtoOptional.get();
    }

    @Override
    @Transactional
    public IngredientDto saveIngredientDto(IngredientDto ingredientDto) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientDto.getRecipeId());

        if (recipeOptional.isEmpty()) {
            //todo toss error if not found!
            log.error("Recipe not found for id: " + ingredientDto.getRecipeId());
            return new IngredientDto();
        } else {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientDto.getId()))
                    .findFirst();

            if (ingredientOptional.isPresent()) {
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(ingredientDto.getDescription());
                ingredientFound.setAmount(ingredientDto.getAmount());
                ingredientFound.setUnitOfMeasure(unitOfMeasureRepository
                        .findById(ingredientDto.getUnitOfMeasure().getId())
                        .orElseThrow(() -> new RuntimeException("UnitOfMeasure Not Found!"))); //todo address this
            } else {
                Ingredient ingredient = ingredientDtoToIngredient.convert(ingredientDto);
                ingredient.setRecipe(recipe);
                recipe.addIngredient(ingredient);
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(ingredientDto.getId()))
                    .findFirst();

            if (savedIngredientOptional.isEmpty()) {
                savedIngredientOptional = savedRecipe.getIngredients().stream()
                        .filter(recipeIngredients -> recipeIngredients.getDescription().equals(ingredientDto.getDescription()))
                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(ingredientDto.getAmount()))
                        .filter(recipeIngredients -> recipeIngredients.getUnitOfMeasure().getId().equals(ingredientDto.getUnitOfMeasure().getId()))
                        .findFirst();
            }

            return ingredientToIngredientDto.convert(savedIngredientOptional.get());
        }
    }

    @Override
    public void deleteById(Long recipeId, Long id) {
        log.debug("Deleting ingredient: " + recipeId + ":" + id);

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (recipeOptional.isPresent()) {
            Recipe recipe = recipeOptional.get();
            log.debug("Found recipe");

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(id))
                    .findFirst();

            if (ingredientOptional.isPresent()) {
                log.debug("Found Ingredient");

                Ingredient ingredientToDelete = ingredientOptional.get();
                ingredientToDelete.setRecipe(null);
                recipe.getIngredients().remove(ingredientOptional.get());
                recipeRepository.save(recipe);
            }
        } else {
            log.debug("Recipe Id Not found. Id: " + recipeId);
        }
    }

}
