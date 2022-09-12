package alex.klimchuk.recipe.services;

import alex.klimchuk.recipe.dto.IngredientDto;
import alex.klimchuk.recipe.converters.*;
import alex.klimchuk.recipe.domain.*;
import alex.klimchuk.recipe.repositories.*;
import alex.klimchuk.recipe.services.impl.IngredientServiceImpl;
import org.junit.*;
import org.mockito.*;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Copyright Alex Klimchuk (c) 2022.
 */
public class IngredientServiceImplTest {

    private final IngredientToIngredientDto ingredientToIngredientDto;
    private final IngredientDtoToIngredient ingredientDtoToIngredient;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    IngredientService ingredientService;

    public IngredientServiceImplTest() {
        this.ingredientToIngredientDto = new IngredientToIngredientDto(new UnitOfMeasureToUnitOfMeasureDto());
        this.ingredientDtoToIngredient = new IngredientDtoToIngredient(new UnitOfMeasureDtoToUnitOfMeasure());
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        ingredientService = new IngredientServiceImpl(ingredientToIngredientDto, ingredientDtoToIngredient,
                recipeRepository, unitOfMeasureRepository);
    }

    @Test
    public void testFindByRecipeIdAndRecipeIdHappyPath() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(1L);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(3L);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        IngredientDto ingredientDto = ingredientService.findByRecipeIdAndIngredientId(1L, 3L);

        assertEquals(Long.valueOf(3L), ingredientDto.getId());
        assertEquals(Long.valueOf(1L), ingredientDto.getRecipeId());
        verify(recipeRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testSaveRecipeDto() {
        IngredientDto ingredientDtoMock = new IngredientDto();
        ingredientDtoMock.setId(3L);
        ingredientDtoMock.setRecipeId(2L);

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(3L);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        IngredientDto savedDto = ingredientService.saveIngredientDto(ingredientDtoMock);

        assertEquals(Long.valueOf(3L), savedDto.getId());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    public void testDeleteById() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(3L);

        Recipe recipe = new Recipe();
        recipe.addIngredient(ingredient);

        ingredient.setRecipe(recipe);

        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        ingredientService.deleteById(1L, 3L);

        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

}
