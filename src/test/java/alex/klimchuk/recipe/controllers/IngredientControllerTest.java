package alex.klimchuk.recipe.controllers;

import alex.klimchuk.recipe.dto.IngredientDto;
import alex.klimchuk.recipe.dto.RecipeDto;
import alex.klimchuk.recipe.services.IngredientService;
import alex.klimchuk.recipe.services.RecipeService;
import alex.klimchuk.recipe.services.UnitOfMeasureService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Copyright Alex Klimchuk (c) 2022.
 */
public class IngredientControllerTest {

    @Mock
    IngredientService ingredientService;

    @Mock
    UnitOfMeasureService unitOfMeasureService;

    @Mock
    RecipeService recipeService;

    IngredientController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        controller = new IngredientController(ingredientService, recipeService, unitOfMeasureService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testListIngredients() throws Exception {
        RecipeDto recipeDtoMock = new RecipeDto();

        when(recipeService.findDtoById(anyLong())).thenReturn(recipeDtoMock);

        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"));

        verify(recipeService, times(1)).findDtoById(anyLong());
    }

    @Test
    public void testShowIngredient() throws Exception {
        IngredientDto ingredientDtoMock = new IngredientDto();

        when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientDtoMock);

        mockMvc.perform(get("/recipe/1/ingredient/2/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(model().attributeExists("ingredient"));
    }

    @Test
    public void testNewIngredientForm() throws Exception {
        RecipeDto recipeDtoMock = new RecipeDto();
        recipeDtoMock.setId(1L);

        when(recipeService.findDtoById(anyLong())).thenReturn(recipeDtoMock);
        when(unitOfMeasureService.findAll()).thenReturn(new HashSet<>());

        mockMvc.perform(get("/recipe/1/ingredient/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientForm"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));

        verify(recipeService, times(1)).findDtoById(anyLong());
    }

    @Test
    public void testUpdateIngredientForm() throws Exception {
        IngredientDto ingredientDtoMock = new IngredientDto();

        when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientDtoMock);
        when(unitOfMeasureService.findAll()).thenReturn(new HashSet<>());

        mockMvc.perform(get("/recipe/1/ingredient/2/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientForm"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));
    }

    @Test
    public void testSaveOrUpdate() throws Exception {
        IngredientDto ingredientDtoMock = new IngredientDto();
        ingredientDtoMock.setId(3L);
        ingredientDtoMock.setRecipeId(2L);

        when(ingredientService.saveIngredientDto(any())).thenReturn(ingredientDtoMock);

        mockMvc.perform(post("/recipe/2/ingredient")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", "")
                        .param("description", "some string"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/ingredient/3/show"));
    }

    @Test
    public void testDeleteIngredient() throws Exception {
        mockMvc.perform(get("/recipe/2/ingredient/3/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/ingredients"));

        verify(ingredientService, times(1)).deleteById(anyLong(), anyLong());
    }

}
