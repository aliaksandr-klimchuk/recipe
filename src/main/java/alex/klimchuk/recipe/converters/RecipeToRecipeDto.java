package alex.klimchuk.recipe.converters;

import alex.klimchuk.recipe.domain.Category;
import alex.klimchuk.recipe.domain.Recipe;
import alex.klimchuk.recipe.dto.RecipeDto;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Copyright Alex Klimchuk (c) 2022.
 */
@Component
public class RecipeToRecipeDto implements Converter<Recipe, RecipeDto> {

    private final IngredientToIngredientDto ingredientConverter;
    private final CategoryToCategoryDto categoryConverter;
    private final NotesToNotesDto notesConverter;

    public RecipeToRecipeDto(IngredientToIngredientDto ingredientConverter, CategoryToCategoryDto categoryConverter,
                             NotesToNotesDto notesConverter) {
        this.ingredientConverter = ingredientConverter;
        this.categoryConverter = categoryConverter;
        this.notesConverter = notesConverter;
    }

    @Override
    @Nullable
    @Synchronized
    public RecipeDto convert(Recipe recipe) {
        RecipeDto recipeDto = RecipeDto.builder()
                .id(recipe.getId())
                .cookTime(recipe.getCookTime())
                .prepTime(recipe.getPrepTime())
                .description(recipe.getDescription())
                .difficulty(recipe.getDifficulty())
                .directions(recipe.getDirections())
                .servings(recipe.getServings())
                .source(recipe.getSource())
                .image(recipe.getImage())
                .url(recipe.getUrl())
                .notes(notesConverter.convert(recipe.getNotes()))
                .build();

        boolean isHasCategories = Objects.nonNull(recipeDto.getCategories()) && !recipeDto.getCategories().isEmpty();
        boolean isHasIngredients = Objects.nonNull(recipeDto.getIngredients()) && !recipeDto.getIngredients().isEmpty();

        if (isHasCategories) {
            recipe.getCategories()
                    .forEach((Category category) -> recipeDto.getCategories().add(categoryConverter.convert(category)));
        }

        if (isHasIngredients) {
            recipe.getIngredients()
                    .forEach(ingredient -> recipeDto.getIngredients().add(ingredientConverter.convert(ingredient)));
        }

        return recipeDto;
    }

}
