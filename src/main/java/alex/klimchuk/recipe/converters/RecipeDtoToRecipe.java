package alex.klimchuk.recipe.converters;

import alex.klimchuk.recipe.dto.RecipeDto;
import alex.klimchuk.recipe.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Copyright Alex Klimchuk (c) 2022.
 */
@Component
public class RecipeDtoToRecipe implements Converter<RecipeDto, Recipe> {

    private final IngredientDtoToIngredient ingredientConverter;
    private final CategoryDtoToCategory categoryConverter;
    private final NotesDtoToNotes notesConverter;

    public RecipeDtoToRecipe(IngredientDtoToIngredient ingredientConverter, CategoryDtoToCategory categoryConverter,
                             NotesDtoToNotes notesConverter) {
        this.ingredientConverter = ingredientConverter;
        this.categoryConverter = categoryConverter;
        this.notesConverter = notesConverter;
    }

    @Override
    @Nullable
    @Synchronized
    public Recipe convert(RecipeDto recipeDto) {
        Recipe recipe = Recipe.builder()
                .id(recipeDto.getId())
                .cookTime(recipeDto.getCookTime())
                .prepTime(recipeDto.getPrepTime())
                .description(recipeDto.getDescription())
                .difficulty(recipeDto.getDifficulty())
                .directions(recipeDto.getDirections())
                .servings(recipeDto.getServings())
                .source(recipeDto.getSource())
                .image(recipeDto.getImage())
                .url(recipeDto.getUrl())
                .notes(notesConverter.convert(recipeDto.getNotes()))
                .build();

        if (recipeDto.getCategories() != null && recipeDto.getCategories().size() > 0) {
            recipeDto.getCategories()
                    .forEach(category -> recipe.getCategories().add(categoryConverter.convert(category)));
        }

        if (recipeDto.getIngredients() != null && recipeDto.getIngredients().size() > 0) {
            recipeDto.getIngredients()
                    .forEach(ingredient -> recipe.getIngredients().add(ingredientConverter.convert(ingredient)));
        }

        return recipe;
    }

}
