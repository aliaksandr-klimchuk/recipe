package alex.klimchuk.recipe.controllers;

import alex.klimchuk.recipe.dto.RecipeDto;
import alex.klimchuk.recipe.handlers.ControllerExceptionHandler;
import alex.klimchuk.recipe.services.ImageService;
import alex.klimchuk.recipe.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Copyright Alex Klimchuk (c) 2022.
 */
public class ImageControllerTest {

    @Mock
    ImageService imageService;

    @Mock
    RecipeService recipeService;

    ImageController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        controller = new ImageController(imageService, recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    public void testGetImageForm() throws Exception {
        RecipeDto recipeDtoMock = new RecipeDto();
        recipeDtoMock.setId(1L);

        when(recipeService.findDtoById(anyLong())).thenReturn(recipeDtoMock);

        mockMvc.perform(get("/recipe/1/image"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"));

        verify(recipeService, times(1)).findDtoById(anyLong());
    }

    @Test
    public void testSaveImageToDB() throws Exception {
        MockMultipartFile multipartFile =
                new MockMultipartFile("imagefile", "testing.txt", "text/plain",
                        "Spring Framework Guru".getBytes());

        mockMvc.perform(multipart("/recipe/1/image").file(multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/recipe/1/show"));

        verify(imageService, times(1)).saveImageFile(anyLong(), any());
    }


    @Test
    public void testRenderImageFromDB() throws Exception {
        String s = "Fake image text";
        Byte[] bytesBoxed = new Byte[s.getBytes().length];

        int i = 0;

        for (byte primByte : s.getBytes()) {
            bytesBoxed[i++] = primByte;
        }

        RecipeDto recipeDtoMock = new RecipeDto();
        recipeDtoMock.setId(1L);
        recipeDtoMock.setImage(bytesBoxed);

        when(recipeService.findDtoById(anyLong())).thenReturn(recipeDtoMock);

        MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipeImage"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        byte[] bytesFromResponse = response.getContentAsByteArray();

        assertEquals(s.getBytes().length, bytesFromResponse.length);
    }

    @Test
    public void testGetImageNumberFormatException() throws Exception {
        mockMvc.perform(get("/recipe/asdf/recipeImage"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("errorPage400"));
    }

}
