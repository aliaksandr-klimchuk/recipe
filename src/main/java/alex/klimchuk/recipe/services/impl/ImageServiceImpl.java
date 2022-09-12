package alex.klimchuk.recipe.services.impl;

import alex.klimchuk.recipe.domain.Recipe;
import alex.klimchuk.recipe.repositories.RecipeRepository;
import alex.klimchuk.recipe.services.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Copyright Alex Klimchuk (c) 2022.
 */
@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeService) {
        this.recipeRepository = recipeService;
    }

    @Override
    @Transactional
    public void saveImageFile(Long recipeId, MultipartFile file) {
        try {
            Byte[] byteObjects = new Byte[file.getBytes().length];

            int i = 0;

            for (byte b : file.getBytes()) {
                byteObjects[i++] = b;
            }

            Recipe recipe = recipeRepository.findById(recipeId).get();
            recipe.setImage(byteObjects);
            recipeRepository.save(recipe);
        } catch (IOException e) {
            //todo handle better
            log.error("Error occurred: ", e);
            e.printStackTrace();
        }
    }

}
