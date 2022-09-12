package alex.klimchuk.recipe.controllers;

import alex.klimchuk.recipe.domain.Recipe;
import alex.klimchuk.recipe.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilderSupport;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Copyright Alex Klimchuk (c) 2022.
 */
public class IndexControllerTest {

    @Mock
    RecipeService recipeService;

    @Mock
    Model model;

    IndexController indexController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        indexController = new IndexController(recipeService);
    }

    @Test
    public void testMockMVC() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void testGetIndexPage() {
        Set<Recipe> recipesMock = new HashSet<>();
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipesMock.add(recipe);

        when(recipeService.getRecipes()).thenReturn(recipesMock);

        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        Set<Recipe> recipes = argumentCaptor.getValue();
        String viewName = indexController.getIndexPage(model);

        assertEquals("index", viewName);
        assertEquals(1, recipes.size());
        verify(recipeService, times(1)).getRecipes();
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
    }

}
