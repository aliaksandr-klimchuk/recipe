package alex.klimchuk.recipe.services;

import alex.klimchuk.recipe.converters.RecipeDtoToRecipe;
import alex.klimchuk.recipe.converters.RecipeToRecipeDto;
import alex.klimchuk.recipe.domain.Recipe;
import alex.klimchuk.recipe.dto.RecipeDto;
import alex.klimchuk.recipe.repositories.RecipeRepository;
import alex.klimchuk.recipe.services.impl.RecipeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Copyright Alex Klimchuk (c) 2022.
 */
public class RecipeServiceImplTest {

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
    public void testGetRecipes() {
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
    public void testGetRecipeById() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        Recipe recipeReturned = recipeService.findById(1L);

        assertNotNull("Null recipe returned", recipeReturned);
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    public void testGetRecipeDtoById() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        RecipeDto recipeDtoMock = new RecipeDto();
        recipeDtoMock.setId(1L);

        when(recipeToRecipeDto.convert(any())).thenReturn(recipeDtoMock);

        RecipeDto recipeDto = recipeService.findDtoById(1L);

        assertNotNull("Null recipe returned", recipeDto);
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    public void testDeleteById() {
        recipeService.deleteById(2L);

        verify(recipeRepository, times(1)).deleteById(anyLong());
    }

}
