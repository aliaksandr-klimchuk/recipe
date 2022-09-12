package alex.klimchuk.recipe.services;

import alex.klimchuk.recipe.domain.Recipe;
import alex.klimchuk.recipe.repositories.RecipeRepository;
import alex.klimchuk.recipe.services.impl.ImageServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Copyright Alex Klimchuk (c) 2022.
 */
public class ImageServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    ImageService imageService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        imageService = new ImageServiceImpl(recipeRepository);
    }

    @Test
    public void testSaveImageFile() throws Exception {
        MultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain",
                "Spring Framework Guru".getBytes());
        Long id = 1L;

        Recipe recipe = new Recipe();
        recipe.setId(id);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        imageService.saveImageFile(id, multipartFile);

        verify(recipeRepository, times(1)).save(argumentCaptor.capture());
        Recipe savedRecipe = argumentCaptor.getValue();
        assertEquals(multipartFile.getBytes().length, savedRecipe.getImage().length);
    }

}
