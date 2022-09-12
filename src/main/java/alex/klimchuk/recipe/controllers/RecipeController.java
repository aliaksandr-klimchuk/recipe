package alex.klimchuk.recipe.controllers;

import alex.klimchuk.recipe.dto.RecipeDto;
import alex.klimchuk.recipe.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Copyright Alex Klimchuk (c) 2022.
 */
@Controller
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{id}/show")
    public String showById(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));
        return "recipe/show";
    }

    @GetMapping("/recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeDto());
        return "recipe/recipeForm";
    }

    @GetMapping("/recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findDtoById(Long.valueOf(id)));
        return "recipe/recipeForm";
    }

    @PostMapping("/recipe")
    public String saveOrUpdate(@ModelAttribute RecipeDto recipeDto) {
        RecipeDto savedRecipeDto = recipeService.saveRecipeDto(recipeDto);
        return "redirect:/recipe/show/" + savedRecipeDto.getId();
    }

    @GetMapping("recipe/{id}/delete")
    public String deleteById(@PathVariable String id) {
        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }

}
