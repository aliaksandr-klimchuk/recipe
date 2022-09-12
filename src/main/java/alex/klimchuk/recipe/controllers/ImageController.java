package alex.klimchuk.recipe.controllers;

import alex.klimchuk.recipe.dto.RecipeDto;
import alex.klimchuk.recipe.services.ImageService;
import alex.klimchuk.recipe.services.RecipeService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * Copyright Alex Klimchuk (c) 2022.
 */
@Controller
public class ImageController {

    private final ImageService imageService;
    private final RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("recipe/{id}/image")
    public String showUploadForm(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findDtoById(Long.valueOf(id)));
        return "recipe/imageUploadForm";
    }

    @PostMapping("recipe/{id}/image")
    public String saveImageFile(@PathVariable String id, @RequestParam("imagefile") MultipartFile file) {
        imageService.saveImageFile(Long.valueOf(id), file);
        return "redirect:/recipe/" + id + "/show";
    }

    @GetMapping("recipe/{id}/recipeimage")
    public void renderImageFromDB(@PathVariable String id, HttpServletResponse response) throws IOException {
        RecipeDto recipeDto = recipeService.findDtoById(Long.valueOf(id));

        if (recipeDto.getImage() != null) {
            byte[] byteArray = new byte[recipeDto.getImage().length];
            int i = 0;

            for (Byte wrappedByte : recipeDto.getImage()) {
                byteArray[i++] = wrappedByte;
            }

            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(byteArray);
            IOUtils.copy(is, response.getOutputStream());
        }
    }

}
