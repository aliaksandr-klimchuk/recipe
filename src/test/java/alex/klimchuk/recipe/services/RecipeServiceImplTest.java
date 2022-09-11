package alex.klimchuk.recipe.services;

import alex.klimchuk.recipe.converters.RecipeDtoToRecipe;
import alex.klimchuk.recipe.converters.RecipeToRecipeDto;
import alex.klimchuk.recipe.domain.Recipe;
import alex.klimchuk.recipe.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Copyright Alex Klimchuk (c) 2022.
 */
class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeToRecipeDto recipeToRecipeDto;

    @Mock
    RecipeDtoToRecipe recipeDtoToRecipe;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository, recipeDtoToRecipe, recipeToRecipeDto);
    }

    @Test
    public void getRecipesTest() {
        Recipe recipe = new Recipe();
        Set<Recipe> recipesMock = new HashSet<>();
        recipesMock.add(recipe);

        when(recipeService.getRecipes()).thenReturn(recipesMock);

        Set<Recipe> recipes = recipeService.getRecipes();

        assertEquals(recipes.size(), 1);
        verify(recipeRepository, times(1)).findAll();
        verify(recipeRepository, never()).findById(anyLong());
    }

    @Test
    public void getRecipeByIdTest() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        Recipe recipeReturned = recipeService.findById(1L);

        assertNotNull("Null recipe returned", recipeReturned);
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }

}