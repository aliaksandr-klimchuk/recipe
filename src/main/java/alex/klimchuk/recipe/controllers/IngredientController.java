package alex.klimchuk.recipe.controllers;

import alex.klimchuk.recipe.dto.IngredientDto;
import alex.klimchuk.recipe.dto.RecipeDto;
import alex.klimchuk.recipe.dto.UnitOfMeasureDto;
import alex.klimchuk.recipe.services.IngredientService;
import alex.klimchuk.recipe.services.RecipeService;
import alex.klimchuk.recipe.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Copyright Alex Klimchuk (c) 2022.
 */
@Slf4j
@Controller
@RequestMapping("/recipe")
public class IngredientController {

    private final IngredientService ingredientService;
    private final RecipeService recipeService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(IngredientService ingredientService, RecipeService recipeService,
                                UnitOfMeasureService unitOfMeasureService) {
        this.ingredientService = ingredientService;
        this.recipeService = recipeService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("/{recipeId}/ingredients")
    public String listIngredients(@PathVariable String recipeId, Model model) {
        log.debug("Getting ingredient list for recipe id: " + recipeId);

        model.addAttribute("recipe", recipeService.findDtoById(Long.valueOf(recipeId)));
        return "recipe/ingredient/list";
    }

    @GetMapping("/{recipeId}/ingredient/{id}/show")
    public String showRecipeIngredient(@PathVariable String recipeId,
                                       @PathVariable String id, Model model) {
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));
        return "recipe/ingredient/show";
    }

    @GetMapping("/{recipeId}/ingredient/new")
    public String newRecipe(@PathVariable String recipeId, Model model) {
        RecipeDto recipeDto = recipeService.findDtoById(Long.valueOf(recipeId));

        IngredientDto ingredientDto = new IngredientDto();
        ingredientDto.setRecipeId(Long.valueOf(recipeId));
        model.addAttribute("ingredient", ingredientDto);

        ingredientDto.setUnitOfMeasure(new UnitOfMeasureDto());

        model.addAttribute("uomList", unitOfMeasureService.findAll());
        return "recipe/ingredient/ingredientForm";
    }

    @GetMapping("/{recipeId}/ingredient/{id}/update")
    public String updateRecipeIngredient(@PathVariable String recipeId,
                                         @PathVariable String id, Model model) {
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));
        model.addAttribute("uomList", unitOfMeasureService.findAll());
        return "recipe/ingredient/ingredientForm";
    }

    @PostMapping("/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientDto ingredientDto) {
        IngredientDto savedDto = ingredientService.saveIngredientDto(ingredientDto);

        log.debug("Saved recipe id: " + savedDto.getRecipeId());
        log.debug("Saved ingredient id: " + savedDto.getId());

        return "redirect:/recipe/" + savedDto.getRecipeId() + "/ingredient/" + savedDto.getId() + "/show";
    }

    @GetMapping("/{recipeId}/ingredient/{id}/delete")
    public String deleteIngredient(@PathVariable String recipeId,
                                   @PathVariable String id) {
        log.debug("Deleting ingredient id: " + id);
        ingredientService.deleteById(Long.valueOf(recipeId), Long.valueOf(id));

        return "redirect:/recipe/" + recipeId + "/ingredients";
    }

}
